package com.project.have_it.Member;

import com.project.have_it.DTO.CustomUser;
import com.project.have_it.DTO.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(MemberDto memberDto) {
        var hashedPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(hashedPassword);
        member.setDisplayName(memberDto.getDisplayName());
        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("등록된 회원이 아닙니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));

        return new CustomUser(
                member.getUsername(),
                member.getPassword(),
                authorities,
                member.getDisplayName()
        );
    }


}