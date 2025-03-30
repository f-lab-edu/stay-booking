package booking_stay.booking_stay.couponevent.service.impl;

import booking_stay.booking_stay.couponevent.domain.entity.CouponEvent;
import booking_stay.booking_stay.couponevent.domain.entity.CouponEventRequest;
import booking_stay.booking_stay.couponevent.domain.enums.CouponEventStatus;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRedisRepository;
import booking_stay.booking_stay.couponevent.domain.repository.CouponEventRepository;
import booking_stay.booking_stay.couponevent.dto.CouponEventCreateRequestDto;
import booking_stay.booking_stay.usercontents.domain.entity.MemberCoupon;
import booking_stay.booking_stay.usercontents.domain.repository.MemberCouponRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CouponEventIssueServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(CouponEventIssueServiceImplTest.class);
    @Autowired
    private CouponEventServiceImpl couponEventService;

    @Autowired
    private CouponEventIssueServiceImpl couponEventIssueService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponEventRepository couponEventRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CouponEventRedisRepository couponEventRedisRepository;

    private CouponEvent defaultCouponEvent;
    private CouponEvent checkExceptionCouponEvent;
    
    @BeforeEach
    void setUp() {
        CouponEventCreateRequestDto requestDto =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트1")
                        .issuedCouponId(1L)
                        .maxQuantity(2000)
                        .status(CouponEventStatus.READY)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        defaultCouponEvent = couponEventService.createCouponEvent(requestDto);
        defaultCouponEvent.changeStatusDo();
        couponEventRedisRepository.setCouponMaxQuantity(defaultCouponEvent.getId(),defaultCouponEvent.getMaxQuantity());

        log.info("시작시점 쿠폰잔고{}",couponEventRedisRepository.getCouponEventMaxQuantity(defaultCouponEvent.getId()));
        log.info("시작시점 큐에 쌓여있는 대기량{}",couponEventRedisRepository.getExistCouponEventCount());




        CouponEventCreateRequestDto requestDto2 =
                CouponEventCreateRequestDto.builder()
                        .eventName("쿠폰지급이벤트2")
                        .issuedCouponId(2L)
                        .maxQuantity(5000)
                        .status(CouponEventStatus.DO)
                        .startTime(LocalDateTime.now().minusDays(2))
                        .endTime(LocalDateTime.now().plusDays(2))
                        .build();

        checkExceptionCouponEvent = couponEventService.createCouponEvent(requestDto2);
    }

    @Test
    public void 쿠폰_이벤트_참여_테스트() throws Exception{
        //given
        CouponEventRequest request = CouponEventRequest.builder()
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .userId("테스트아이디1")
                .build();
        //when
        String result = couponEventIssueService.couponEventProducer(request);

        //then
        assertEquals("참여 완료",result);
    }

    @Test
    @Transactional
    public void 쿠폰_이벤트_중복_참여_테스트() throws Exception{
        //given
        MemberCoupon alreadyExistMemberCoupon = MemberCoupon.builder()
                .userId("테스트아이디1")
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .build();

        memberCouponRepository.save(alreadyExistMemberCoupon);
        entityManager.flush();
        entityManager.clear();

        //when
        CouponEventRequest duplicatedRequest = CouponEventRequest.builder()
                .couponId(defaultCouponEvent.getIssuedCouponId())
                .couponEventId(defaultCouponEvent.getId())
                .userId("테스트아이디1")
                .build();

        String result = couponEventIssueService.couponEventProducer(duplicatedRequest);

        //then
        assertEquals("중복참여 불가",result);
    }

    @Test
    public void 쿠폰이벤트_시작시_상태값과_수량_세팅() throws Exception{
        //given
        Long couponId = defaultCouponEvent.getIssuedCouponId();

        //when
        couponEventIssueService.turnOnCouponEvent(couponId);

        //then
        CouponEvent couponEvent = couponEventRepository.findCouponEventById(couponId).orElseThrow();
        int couponEventMaxQuantity = couponEventRedisRepository.getCouponEventMaxQuantity(couponId);

        assertEquals(CouponEventStatus.READY, couponEvent.getStatus());
        assertEquals(couponEventMaxQuantity, couponEvent.getMaxQuantity());

    }

    @Test
    public void 쿠폰이벤트_쿠폰수량_초기화() throws Exception{
        //given
        Long couponId = defaultCouponEvent.getIssuedCouponId();

        //when
        couponEventIssueService.resetCouponEventCount(couponId);

        //then
        int couponEventMaxQuantity = couponEventRedisRepository.getCouponEventMaxQuantity(couponId);
        assertEquals(couponEventMaxQuantity, defaultCouponEvent.getMaxQuantity());

    }
    
    // 3000명이 들어왔을때 1000개가 잘 지급되는지
    @Test
    @Transactional
    public void 동시성_1000명_테스트() throws InterruptedException {
        //given
        Long couponId = defaultCouponEvent.getIssuedCouponId();

        int totalTestCount = 1200;
        ExecutorService executorService = Executors.newFixedThreadPool(totalTestCount);
        CountDownLatch countDownLatch = new CountDownLatch(totalTestCount);

        List<CouponEventRequest> couponEventRequests = new ArrayList<>();
        for (int i = 0; i < totalTestCount; i++) {
            CouponEventRequest request = CouponEventRequest.builder()
                    .couponId(defaultCouponEvent.getIssuedCouponId())
                    .couponEventId(defaultCouponEvent.getId())
                    .userId("테스트아이디" + i)
                    .build();
            couponEventRequests.add(request);
        }
        
        //when
        for (int i = 0; i < totalTestCount; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    String result = couponEventIssueService.couponEventProducer(couponEventRequests.get(finalI));
                    assertEquals("참여 완료",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        while(couponEventRedisRepository.getCouponEventMaxQuantity(couponId) < 1){
            log.info(String.valueOf(couponEventRedisRepository.getCouponEventMaxQuantity(couponId)));
        }
//        Thread.sleep();
        Thread.sleep(18000);
        //then
//        assertEquals(0>couponEventRedisRepository.getCouponEventMaxQuantity(couponId), couponEventRedisRepository.getCouponEventMaxQuantity(couponId));
        assertEquals(800, couponEventRedisRepository.getCouponEventMaxQuantity(couponId));
        assertEquals(1200, memberCouponRepository.countMemberCouponByCouponEventId(couponId));

        int stock = couponEventRedisRepository.getCouponEventMaxQuantity(defaultCouponEvent.getId());
        log.info("남은수량{}",stock);

        int issuedMemberCnt = memberCouponRepository.countMemberCouponByCouponEventId(defaultCouponEvent.getId());
        log.info("발급된 멤버 수량{}",issuedMemberCnt);



    }
//    scheduler 시간을 줄이던 한번에 여러명을 하던 느릴거다 비동기를 돌려서 여러 쓰레드로 처리하는 방법도 있다.

//    @Test
//    @Transactional
//    @DisplayName("쿠폰 이벤트 10000명 참여 테스트")
//    public void thousandsProducerTest() throws ExecutionException, InterruptedException {
//        //given
//
//
//        int testCount = 1100;
//
//        List<CouponEventRequest> requestList = new ArrayList<>();
//        for (int i = 0; i < testCount; i++) {
//            CouponEventRequest request = CouponEventRequest.builder()
//                    .couponId(defaultCouponEvent.getIssuedCouponId())
//                    .couponEventId(defaultCouponEvent.getId())
//                    .userId("테스트아이디" + i)
//                    .build();
//
//            requestList.add(request);
//        }
//        //when
//        int cnt = 0;
//        ExecutorService executorService = Executors.newFixedThreadPool(30);
//        List<Future<String>> futures = new ArrayList<>();
//        try{
//            for (int i = 0; i < testCount; i++) {
//                int finalI = i;
//                Callable<String> task = () -> couponEventIssueService.couponEventProducer(requestList.get(finalI));
//                futures.add(executorService.submit(task));
//                cnt++;
//            }
//        }finally {
//            executorService.shutdown();
//        }
//
//
////        Thread.sleep(10000);
////        then
//        for (Future<String> future : futures) {
//            String result = future.get();
//            assertEquals("addQueue 성공", result);
//        }
//
//
//        Thread.sleep(30000);
//        log.info("총 발행량{}", cnt);
//
//        int stock = couponEventRedisRepository.getCouponEventMaxQuantity(defaultCouponEvent.getId());
//        log.info("남은수량{}",stock);
//
//        int issuedMemberCnt = memberCouponRepository.countMemberCouponByCouponEventId(defaultCouponEvent.getId());
//        log.info("발급된 멤버 수량{}",issuedMemberCnt);
//
//    }


}