package com.fastcampus.miniproject.service;

import com.fastcampus.miniproject.common.JoinException;
import com.fastcampus.miniproject.dto.request.JoinMemberRequest;
import com.fastcampus.miniproject.dto.request.MemberDetailRequest;
import com.fastcampus.miniproject.dto.response.GetMemberResponse;
import com.fastcampus.miniproject.dto.request.UpdateMemberRequest;
import com.fastcampus.miniproject.entity.AdditionalInfo;
import com.fastcampus.miniproject.entity.Member;
import com.fastcampus.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void inputDetails(Long memberId, MemberDetailRequest request) {
        Member member = findById(memberId);
        member.changeAdditionalInfo(
                request.getJob(),
                request.getInterest(),
                request.getRealEstate(),
                request.getCar(),
                request.getAsset(),
                request.getSalary(),
                request.getAge());
    }

    @Transactional
    public void updateJoinInfo(Long memberId, UpdateMemberRequest request) {
        Member member = findById(memberId);
        if (request.getPassword() != null)
            member.changePassword(request.getPassword());

        if(request.getName() != null)
            member.changeName(request.getName());

        if(request.getPhoneNumber() != null)
            member.changePhoneNumber(request.getPhoneNumber());
    }

    @Transactional
    public void updateAdditionalInfo(Long memberId, UpdateMemberRequest request) {
        Member member = findById(memberId);
        AdditionalInfo info = member.getAdditionalInfo();

        member.changeAdditionalInfo(
                (request.getJob() == null) ? info.getJob() : request.getJob(),
                (request.getInterest() == null) ? List.of(info.getInterest().split("\\|")) : request.getInterest(),
                (request.getRealEstate() == null) ? info.getRealEstate() : request.getRealEstate(),
                (request.getCar() == null) ? info.getCar() : request.getCar(),
                (request.getAsset() == null) ? info.getAsset() : request.getAsset(),
                (request.getSalary() == null) ? info.getSalary() : request.getSalary(),
                (request.getAge() == null) ? info.getAge() : request.getAge());
    }

    @Transactional
    public void joinMember(JoinMemberRequest joinMemberRequest) {
        Optional<Member> targetMember = memberRepository.findMemberByLoginId(joinMemberRequest.getEmail());
        if (targetMember.isPresent()) {
            throw new JoinException("이미 가입되어 있는 유저입니다.");
        }
        String encodingPassword = encodingPassword(joinMemberRequest.getPassword());
        joinMemberRequest.setPassword(encodingPassword);
        memberRepository.save(joinMemberRequest.toMember());
    }

    public GetMemberResponse getMember(Long memberId) {
        Member member = findById(memberId);
        return new GetMemberResponse(member.getName(), member.getAdditionalInfo());
    }

    Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
    }

    private String encodingPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
