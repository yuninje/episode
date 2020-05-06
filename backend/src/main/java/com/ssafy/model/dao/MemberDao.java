package com.ssafy.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.Member;
import com.ssafy.model.dto.MemberException;

@Repository
public class MemberDao {
	List<Member> members;
	MemberDao(){
		setMembers();
	}
	
	private void setMembers() {
		members = new ArrayList<Member>();
		for(int i = 0; i<20; i++) {
			String strI = String.valueOf(i);
			Member member = new Member(
					i, 
					"mem_id"+strI, 
					"mem_email"+strI,
					"mem_pw" + strI,
					"mem_nick" + strI,
					"mem_phone" + strI,
					"mem_birth" + strI,
					true
					);
			members.add(member);
		}
	}
	
	public Member getMember(String mem_id) {
		for(Member member : members) {
			if(member.getMem_id().equals(mem_id)) {
				return member;
			}
		}
		return null;
	}
	
	public boolean registMember(Member rMember) {
		for(Member member : members) {
			if(member.getMem_id().equals(rMember.getMem_id())) {
				// 회원가입 실패
				throw new MemberException("아이디 중복");
			}
		}
		
		// 회원가입 성공
		rMember.setMem_pk(members.size());
		members.add(rMember);
		return true;
	}
	
	public List<Member> getMembers() {
		return members;
	}

}
