package com.project.have_it.scrap;

import com.project.have_it.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByMember(Member member);
}
