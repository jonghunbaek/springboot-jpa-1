package jpabook.jpashop.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.sevice.MemberService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	
	@Test
	public void 회원가입() throws Exception {
		//given
		Member member = new Member();
		member.setName("hong");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test
	public void 중복_회원_예외() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("hong");
		
		Member member2 = new Member();
		member2.setName("hong");
		
		//when
		memberService.join(member1);
		assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);
		
	}
}
