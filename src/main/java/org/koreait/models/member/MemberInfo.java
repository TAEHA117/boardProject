package org.koreait.models.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data @Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;

    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override // 로그인할 때
    public String getPassword() {
        return password;
    }

    @Override // 로그인할 때 / 조회할 때
    public String getUsername() {
        return email;
    }

    @Override // 어카운트가 만료되지 않았느냐 -> false -> 만료
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 어카운트가 잠겨있지않느냐 -> false -> 잠겨있다 -> 로그인안됌
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 비밀번호가 만료가 되지 않았느냐 -> false 만료가 됐다 -> 초기화해야함
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 회원탈퇴 여부 / 비활성화 가입 -> 관리자가 승인할 때 / 응용가능
    public boolean isEnabled() {
        return true;
    }
}

