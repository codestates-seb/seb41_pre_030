import { All, AllQuestions, AskQuestionButton } from "./Home"
import styled from "styled-components";
import useFetch from "./util/useFetch";
import { NavLink, useParams } from "react-router-dom";
import OAuthButton from "../Pages/Signup/OAuthButton";
import Google from "../Image/Google";
import GitHub from "../Image/GitHub";
import Logo from "../Image/Logo";
import { CaretUpOutlined, CaretDownOutlined } from '@ant-design/icons' ;
import moment from 'moment';

const Post = styled.div`
  width: auto;
  height: auto;
  display: grid;
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
const QuestionEstimation = styled.span`
  font-size: 20px;
  display: flex;
  text-align: center;
  align-items: center;

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
const SubmitButton = styled(NavLink)`
  background-color: rgba(0, 60, 255, 0.8);
  color:white;
  border: 1px solid blue;
  border-radius: 7px;
  width: 120px;
  height: 40px;
  font-size: 17px;
  margin-top: 10px;
  text-align: center;
  text-decoration: none;
  :hover {
    background-color: blue;
    }
`
const Writer = styled.div`
  text-decoration: none;
  border: 1px solid black;
  background-color: aliceblue;
  width: 150px;
  height: 100px;
  display: flex;
  align-items: center;
`
const HyperLink = styled(NavLink)`
  text-decoration: none;
  color: blue
`

function QuestionDetail () {

  const { id } = useParams()
  const request = {
    method : "get",
    headers : {"Content-Type" : "application/json"}
  }
  //클라이언트가 서버에게 json 형식의 데이터가 보내지는 것인지 알려주는 설정

  const [question] = useFetch(`http://localhost:3001/questions/${id}`,request)
  const [member] = useFetch(`http://localhost:3001/members/${id}`,request)
  
  console.log(question)
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
            <button style={{"backgroundColor" : "white", "border" : "none"}}>
              <CaretUpOutlined style={{"fontSize" : "30px"}}/>
              </button>
                <QuestionEstimation>{question.vote}</QuestionEstimation>
            <button style={{"backgroundColor" : "white", "border" : "none"}}>
              <CaretDownOutlined style={{"fontSize" : "30px"}}/>
              </button>
          </Vote>
        </div>
        <Content>
          {question && (
            <span>{question.content}</span>
          )}
        </Content>
        </QuestionContentView>
        <Writer>
          <div>
            <Date>Asked {question.createdAt}</Date>
            <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{member.nickName}</HyperLink>
          </div>
        </Writer>
      </Post>
      <Answer>
        <AnswerCount>{question && question.answer.length} answers</AnswerCount>
        <AnswerContentView>
          {question && question.answer.map(answer =>
          <div>
          <div style={{"display" : "flex"}} key={answer.id}>
            <Vote>
              <button style={{"backgroundColor" : "white", "border" : "none"}}>
                <CaretUpOutlined style={{"fontSize" : "30px"}}/>
              </button>
              <QuestionEstimation>{answer.vote}</QuestionEstimation>
              <button style={{"backgroundColor" : "white", "border" : "none"}}>
                <CaretDownOutlined style={{"fontSize" : "30px"}}/>
              </button>
            </Vote>
            <Content>
              <div>{answer.content}</div>
            </Content>
          </div>
            <Writer>
              <div>
                <Date>answered {question.createdAt}</Date>
                <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{member.nickName}</HyperLink>
              </div>
            </Writer>
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
          <div style={{"whiteSpace": "nowrap"}} >Signup or
            <HyperLink to="/login"> login</HyperLink>
          </div>
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
              <Logo style={{"height" : "10"}}/> 
              Sign up using Email and Password
            </OAuthButton>
          </div>
        </FlexRight>
        <PostAnswer>
          //새로고침만 
        <SubmitButton to='/'>Post Your Answer</SubmitButton>
          <span style={{"margin" : "18px", "fontSize" : "17px"}}>Not the answer you're looking for? 
          <HyperLink to = "/AskQuestion"> ask your own question</HyperLink></span>
        </PostAnswer>

    </All>
  )
}
export default QuestionDetail

//12.26일 css 정리, 답변 작성하는 칸 만들기, d-day, length issue
