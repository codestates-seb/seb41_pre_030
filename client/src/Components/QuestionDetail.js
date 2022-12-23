import { All, AllQuestions, AskQuestionButton } from "./Home"
import styled from "styled-components";
import useFetch from "./util/useFetch";
import { useParams } from "react-router-dom";
import OAuthButton from "../Pages/Signup/OAuthButton";
import Google from "../Image/Google";
import GitHub from "../Image/GitHub";
import Logo from "../Image/Logo";

const Post = styled.div`
  width: auto;
  height: auto;
`
const Subject = styled.div`
  font-size: 20px;
`
const At = styled.div`
  display: flex;
  border-bottom: 1px solid gray;
  margin: 0 20px;
`
const Content = styled.span`
  margin: 20px 10px 10px 0;
  font-size: 20px;
`

const QuestionContentView = styled.div`
  display: flex;
`

const AnswerContentView = styled.div`
  display: grid;
`

const Vote = styled.div`
  margin: 20px;
  display: grid;
`
const AnswerCount = styled.div`
  margin: 10px;
`
const Date = styled.div`
  font-size: 15px;
  margin: 0px 30px 10px 0px;
`
const Answer = styled.div`
  
`
const YourAnswer = styled.div`
  margin: 20px;
  display: grid;
`
const PostAnswer = styled.div`
  font-size: 15px;
  margin: 15px;
  display: flex;
`

const FlexRight = styled.div`
  display: grid;
  margin: 20px;
  font-size: 20px;
  div.button-group {
    display: flex !important;
    flex-direction: column;
    margin-bottom: 16px;
  }
`


function QuestionDetail () {

  const { id } = useParams()
  const request = {
    method : "get",
    headers : {"Content-Type" : "application/json"}
  }
  //클라이언트가 서버에게 json 형식의 데이터가 보내지는 것인지 알려주는 설정
  const [question] = useFetch(`http://localhost:3001/questions/${id}`,request)
  
  return(
    <All>
      <Post>
        <AllQuestions>
          <Subject>  
            {question && (
              <h2>{question.subject}</h2>
              )}
          </Subject>
          <AskQuestionButton to='/AskQuestion'>Ask Question</AskQuestionButton>          
        </AllQuestions>
        <At>
          <Date>Asked {question.createdAt}</Date>
          <Date>Motified {question.modifiedAt}</Date>
          <Date>Viewed {question.view}</Date>
        </At>
        <QuestionContentView>
        <div style={{"display" : "flex"}}>
          <Vote>
            <button>up</button>
              <span style={{'fontSize':'20px'}}>{question.vote}</span>
            <button>down</button>
          </Vote>
        </div>
          <Content>
            {question && (
              <span>{question.content}</span>
              )}
          </Content>
        </QuestionContentView>
      </Post>
      <Answer>
        <AnswerCount>{question.answer.length} answers</AnswerCount>
        <AnswerContentView>
              {question.answer.map(answer =>
              <div style={{"display" : "flex"}} key={answer.answerId}>
          <Vote>
            <button>up</button>
            <span style={{'fontSize':'20px'}}>{answer.vote}</span>
            <button>down</button>
          </Vote>
          <Content>
                <div>{answer.content}</div>
          </Content>
              </div>
                )}
        </AnswerContentView>
      </Answer>
      <YourAnswer>
        Your Answer
        <input>
        </input>
      </YourAnswer>
        <FlexRight>
          Signup or login
          <div className='button-group'>
            <OAuthButton color="rgb(59, 64, 69)">
              <Google /> 
              Sign up with Google
            </OAuthButton>
            <OAuthButton bg_color="rgb(47, 51, 55)">
              <GitHub /> 
              Sign up with GitHub
            </OAuthButton>
            <OAuthButton bg_color="rgb(56, 84, 153)">
              <Logo /> 
              Sign up using Email and Password
            </OAuthButton>
          </div>
        </FlexRight>
        <PostAnswer>
        <AskQuestionButton>Post Your Answer</AskQuestionButton>
          <span style={{"margin" : "18px"}}>Not the answer you're looking for? ask your own question</span>
        </PostAnswer>

    </All>
  )
}
export default QuestionDetail

//12.26일 css 정리, 답변 작성하는 칸 만들기, 하이퍼링크? d-day, length issue
