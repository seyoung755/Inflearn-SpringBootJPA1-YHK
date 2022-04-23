package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 기능에 대한 최적화를 할 수 있다.
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // em.persist를 할 때 영속성 컨텍스트에 값이 등록되고, id에 값이 들어가게 된다.
    }

    private void validateDuplicateMember(Member member) {
        /*
        이렇게 검증하더라도 멀티 스레드 환경 등의 상황에서 동시 가입이 발생할 수 있다.
        그러므로 DB에 UNIQUE 제약 조건 등을 통해 최후의 방어를 하는 것이 좋다.
         */
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {

    }
}
