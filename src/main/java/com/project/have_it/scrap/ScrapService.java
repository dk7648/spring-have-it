package com.project.have_it.scrap;

import com.project.have_it.Member.Member;
import com.project.have_it.habit.Habit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    public Scrap scrapHabit(Member member, Habit habit) {
        Scrap scrap = new Scrap();
        scrap.setMember(member);
        scrap.setTitle(habit.getTitle());
        scrap.setContent(habit.getContent());
        scrap.setDifficult(habit.getDifficult());
        scrap.setRepeatCount(habit.getRepeatCount());
        scrap.setOriginalHabitId(habit.getId());
        scrap.setScrappedAt(LocalDateTime.now());
        return scrapRepository.save(scrap);
    }

    public List<Scrap> getScrapsByMember(Member member) {
        return scrapRepository.findByMember(member);
    }

    public boolean deleteScrap(Long scrapId, String username) {
        Optional<Scrap> optionalScrap = scrapRepository.findById(scrapId);
        if (optionalScrap.isPresent() && optionalScrap.get().getMember().getUsername().equals(username)) {
            scrapRepository.deleteById(scrapId);
            return true;
        }
        return false;
    }
}
