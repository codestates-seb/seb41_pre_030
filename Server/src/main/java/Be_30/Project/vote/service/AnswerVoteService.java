package Be_30.Project.vote.service;

import Be_30.Project.answer.entity.Answer;
import Be_30.Project.answer.repository.AnswerRepository;
import Be_30.Project.answer.service.AnswerService;
import Be_30.Project.auth.userdetails.MemberDetails;
import Be_30.Project.exception.BusinessLogicException;
import Be_30.Project.exception.ExceptionCode;
import Be_30.Project.member.entity.Member;
import Be_30.Project.member.repository.MemberRepository;
import Be_30.Project.member.service.MemberService;
import Be_30.Project.vote.entity.AnswerVote;
import Be_30.Project.vote.entity.AnswerVote.VoteStatus;
import Be_30.Project.vote.repository.AnswerVoteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerVoteService {

    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;
    private final MemberService memberService;
    private final AnswerService answerService;

    public AnswerVoteService(AnswerRepository answerRepository,
        AnswerVoteRepository answerVoteRepository, MemberService memberService,
        AnswerService answerService) {
        this.answerRepository = answerRepository;
        this.answerVoteRepository = answerVoteRepository;
        this.memberService = memberService;
        this.answerService = answerService;
    }

    public AnswerVote addVoteUp(long answerId, MemberDetails memberDetails) {

        Answer answer = answerService.findVerifiedAnswer(answerId);
        Member member = memberService.findMember(memberDetails.getMemberId(), memberDetails.getEmail());

        AnswerVote answerVote = new AnswerVote();

        if(VerifyOfMemberVotesAnswer(answer, member)) {

            answer.setVotes(answer.getVotes() + 1);
            answerVote.setVoteStatus(VoteStatus.VOTE_UP);

        } else {
            AnswerVote findAnswerVote = findVerifiedAnswerVote(answer, member);
            VoteStatus voteStatus = findAnswerVote.getVoteStatus();

            if(voteStatus.equals(VoteStatus.VOTE_UP)) {

                answer.setVotes(answer.getVotes() -1);
                answerVote.setVoteStatus(VoteStatus.VOTE_CANCEL);

            } else if(voteStatus.equals(VoteStatus.VOTE_DOWN)) {

                answer.setVotes(answer.getVotes() + 1);
                answerVote.setVoteStatus(VoteStatus.VOTE_CANCEL);

            } else if(voteStatus.equals(VoteStatus.VOTE_CANCEL)){

                answer.setVotes(answer.getVotes() + 1);
                answerVote.setVoteStatus(VoteStatus.VOTE_UP);

            }
            answerVoteRepository.delete(findAnswerVote);
        }
        Answer saveAnswer = answerRepository.save(answer);
        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(member);

        return answerVoteRepository.save(answerVote);
    }

    public AnswerVote addVoteDown(long answerId, MemberDetails memberDetails) {
        Answer answer = answerService.findVerifiedAnswer(answerId);
        Member member = memberService.findMember(memberDetails.getMemberId(), memberDetails.getEmail());

        AnswerVote answerVote = new AnswerVote();

        if(VerifyOfMemberVotesAnswer(answer, member)) {

            answer.setVotes(answer.getVotes() - 1);
            answerVote.setVoteStatus(VoteStatus.VOTE_DOWN);

        } else {

            AnswerVote findAnswerVote = findVerifiedAnswerVote(answer, member);
            VoteStatus voteStatus = findAnswerVote.getVoteStatus();

            if(voteStatus.equals(VoteStatus.VOTE_DOWN)) {

                answer.setVotes(answer.getVotes() + 1);
                answerVote.setVoteStatus(VoteStatus.VOTE_CANCEL);

            } else if (voteStatus.equals(VoteStatus.VOTE_UP)) {

                answer.setVotes(answer.getVotes() - 1);
                answerVote.setVoteStatus(VoteStatus.VOTE_CANCEL);

            } else if (voteStatus.equals(VoteStatus.VOTE_CANCEL)) {

                answer.setVotes(answer.getVotes() - 1);
                answerVote.setVoteStatus(VoteStatus.VOTE_DOWN);

            }
            answerVoteRepository.delete(findAnswerVote);
        }
        Answer saveAnswer = answerRepository.save(answer);

        answerVote.setAnswer(saveAnswer);
        answerVote.setMember(member);

        return answerVoteRepository.save(answerVote);
    }

    // 추천, 비추천 내역이 없음을 검증하는 메서드
    private boolean VerifyOfMemberVotesAnswer(Answer answer, Member member) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, member);

        if (optionalAnswerVote.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // 내역이 있는 vote를 검증하는 메서드?
    private AnswerVote findVerifiedAnswerVote(Answer answer, Member member) {
        Optional<AnswerVote> optionalAnswerVote =
            answerVoteRepository.findByAnswerAndMember(answer, member);

        AnswerVote answerVote =
            optionalAnswerVote.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.VOTE_NOT_FOUND)
            );

        return answerVote;
    }
}
