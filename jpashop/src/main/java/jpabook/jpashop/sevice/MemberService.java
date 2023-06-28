package jpabook.jpashop.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	public List<Member> findAll() {
		return memberRepository.findAll();
	}
	
	public Member findOne(Long id) {
		return memberRepository.findOne(id);
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.fineByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
}
