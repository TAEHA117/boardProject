package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Member;
import org.koreait.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService { // info가 들어가면 정보를 조회할 때 주로 사용하자 - 강사님방법

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getMtype().name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword()) //getPassword <- 해쉬처리돼있음
                .authorities(authorities)
                .member(member)
                .build();
    }

}
