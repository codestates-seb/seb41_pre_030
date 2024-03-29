import React, { Fragment, useState } from "react";
import { All, AllQuestions, AskQuestionButton } from "../Home/Home"
import styled from "styled-components";
import useFetch from "../../Components/util/useFetch";
import { Link, NavLink, useParams } from "react-router-dom";
import OAuthButton from "../Signup/OAuthButton";
import Google from "../../Image/Google";
import GitHub from "../../Image/GitHub";
import Logo from "../../Image/Logo";
import axios from 'axios';
import { CaretUpOutlined, CaretDownOutlined, CheckOutlined } from '@ant-design/icons' ;
import ReactQuill from 'react-quill'
import 'react-quill/dist/quill.snow.css';

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
const Content = styled.div`
  margin: 40px 10px 10px 0;
  font-size: 20px;
  min-height: 200px;
  word-wrap: break-word;
`

const QuestionContentView = styled.div`
  display: flex;
  max-width: 1100px;
`

const AnswerContentView = styled.div`
  display: grid;
  .eachAnswer{
    border-bottom: 1px solid gray;
    margin: 20px;
  }
  .ql-editor {
    font-size: 18px;
  }
  .answer-edit .edit-button {
    background: none;
    border: 1px solid #ccc;
    border-radius: 3px;
    height: 30px;
    width: 60px;
    color: #1f9315;
    margin-top: 80px;
    cursor: pointer;
  }
`

const Vote = styled.div`
  margin: 0px 20px;
  display: grid;
  justify-items: center;
`

const QuestionEstimation = styled.span`
  font-size: 20px;
  display: flex;
  text-align: center;
  align-items: center;
`

const Date = styled.div`
  font-size: 15px;
  margin: 0px 10px 10px 0px;
`
const Answer = styled.div`
  margin: 10px 0 10px 25px;
  font-size: 25px;
`
const YourAnswer = styled.div`
  margin: 20px;
  display: grid;
`
const PostAnswer = styled.div`
  font-size: 15px;
  margin: 50px 20px 20px;
  display: flex;
`

const FlexRight = styled.div`
  display: grid;
  margin: 85px 20px 20px 20px;
  font-size: 20px;
  div.button-group {
    display: flex !important;
    flex-direction: column;
    margin-bottom: 16px;
  }
`
const SubmitButton = styled.button`
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;

  border: 1px solid hsl(205deg 41% 63%);
  border-radius: 4px;
  font-weight: 500;

  background-color: hsl(206deg 100% 52%);
  color: #fff;
  width: 120px;
  height: 40px;
  font-size: 17px;
  margin-top: 10px;

  :hover {
    background: hsl(209deg 100% 38%);
  }
  :active {
    background: hsl(209deg 100% 32%);
  }
  
  cursor: pointer;
`
const Writer = styled.div`
  text-decoration: none;
  border-radius: 5px;
  background-color: aliceblue;
  width: 200px;
  height: 130px;
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  float: right;
  margin: 0 10px 20px 0;
  .createUser{
    margin: 10px;
    img {
      margin-right: 10px;
      border-radius: 3px;
    }
  }
  .edit{
    font-size: 13px;
    text-decoration: none;
    color: #1f9315;
    cursor: pointer;
  }
  p{
    font-size: 13px;
    text-decoration: none;
    color: #1f9315
  }
`
const HyperLink = styled(NavLink)`
  text-decoration: none;
  color: blue;
`
const StackLogo = styled.span`
  svg{
    height: 20px;
  }
`;

const AdoptStyle = styled(CheckOutlined)`
  font-size: 20px;
  margin-top: 20px;
`

function QuestionDetail () {

  const { id } = useParams()

  const request = {
    method : "get",
    headers : {"Content-Type" : "application/json"}
  }

  const [question] = useFetch(`http://13.125.30.88:8080/questions/${id}`,request)
  console.log(question)

  const [value, setValue] = useState('');
  const [answerEdit, setAnswerEdit] = useState(false)
  const [answerContent, setAnswerContent] = useState('')

  let isLogin = localStorage.getItem("isLogin");
  let userId = localStorage.getItem("user")

  let createDate = null;
  let modifiedDate = null;
  let answerCreateDate = null;

  if (question) {
    createDate = new window.Date(question.data.createdAt)
    modifiedDate = new window.Date(question.data.modifiedAt)
    answerCreateDate = new window.Date(question.data.createdAt)
  }

  const modules = {
    toolbar: {
        container: [
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          [{ 'font': [] }],
          [{ 'align': [] }],
          ['bold', 'italic', 'underline', 'strike', 'blockquote'],
          [{ 'list': 'ordered' }, { 'list': 'bullet' }, 'link'],
          [{ 'color': ['#000000', '#e60000', '#ff9900', '#ffff00', '#008a00', '#0066cc', '#9933ff', '#ffffff', '#facccc', '#ffebcc', '#ffffcc', '#cce8cc', '#cce0f5', '#ebd6ff', '#bbbbbb', '#f06666', '#ffc266', '#ffff66', '#66b966', '#66a3e0', '#c285ff', '#888888', '#a10000', '#b26b00', '#b2b200', '#006100', '#0047b2', '#6b24b2', '#444444', '#5c0000', '#663d00', '#666600', '#003700', '#002966', '#3d1466', 'custom-color'] }, { 'background': [] }],
          ['image', 'video'],
          ['clean']  
          ]
    }
  }

  const timeForToday = (time) => {
    const today = new window.Date();
    const timeValue = new window.Date(time);
    const betweenTimeMin = Math.floor((today.getTime() - timeValue.getTime())/ 1000 / 60)
    const betweenTimeHour = Math.floor( betweenTimeMin / 60)
    const betweenTimeDay = Math.floor( betweenTimeMin / 60 / 24)

    if(betweenTimeMin < 1) return "방금 전"
    if(betweenTimeMin < 60) return `${betweenTimeMin}분전`
    if(betweenTimeHour < 24) return `${betweenTimeHour} hours ago`
    if(betweenTimeDay < 365) return `${betweenTimeDay} days ago`
  
    return `${Math.floor(betweenTimeDay / 365)} years ago`
  } 

  const handleSubmit = (e) => {
    if(value === '') return alert("내용을 입력하세요")
    axios.post(`http://13.125.30.88:8080/questions/${id}/answers`, JSON.stringify(value),
      {headers : {
        "Content-Type" : "application/json",
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }}
    )
    .then (() => {
      window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
    console.log(e.target.value)
  }

  const [votes, setVotes] = useState(question && question.data.votes)

  const questionUpVoteHandler = () => {
    const updateRequest = {
      method : "POST",
      headers: {
        "Content-Type": 'application/json',
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    }
    fetch(`http://13.125.30.88:8080/questions/${id}/vote-up`, updateRequest)
    .then (() => {
      setVotes(votes + 1)
      window.location.reload();
      // window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
  }

  const questionDownVoteHandler = () => {
    const updateRequest = {
      method : "POST",
      headers: {
        "Content-Type": 'application/json',
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    }
    fetch(`http://13.125.30.88:8080/questions/${id}/vote-down`, updateRequest)
    .then (() => {
      setVotes(votes - 1)
      window.location.reload();
      // window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
    console.log("down")
  }
  
  const answerUpVoteHandler = (answerId) => {
    const updateRequest = {
      method : "POST",
      headers: {
        "Content-Type": 'application/json',
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    }
    fetch(`http://13.125.30.88:8080/questions/${id}/answers/${answerId}/vote-up`, updateRequest)
    .then (() => {
      setVotes(votes + 1)
      window.location.reload();
      // window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
    console.log("up")
  }

  const answerDownVoteHandler = (answerId) => {
    const updateRequest = {
      method : "POST",
      headers: {
        "Content-Type": 'application/json',
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    }
    fetch(`http://13.125.30.88:8080/questions/${id}/answers/${answerId}/vote-down`, updateRequest)
    .then (() => {
      setVotes(votes + 1)
      window.location.reload();
      // window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
    console.log("up")
  }

  const answerEditStateChanger = () => {
    setAnswerEdit(!answerEdit)
  }

  const onAnswerEditHandler = (answerMemberId) => {
    const bodyJSON =  JSON.stringify({
      content: answerContent,
    });

    axios
    .patch(`http://13.125.30.88:8080/questions/${id}/answers/${answerMemberId}`, bodyJSON, {
      headers: {
        "Content-Type": 'application/json',
        "AutHorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    })
    .then((res) => {
      alert("Answer edited!");
      window.location.reload();
    })
    .catch((err) => {
      console.log(err);
    });
  }

  return(
    <All>
      {!question ?? <p>Loading</p>}
      <div>
        <Post>
          <AllQuestions>
            <Subject>  
                <h2>{question && question.data.subject}</h2>
            </Subject>
            <AskQuestionButton to={localStorage.getItem("isLogin") ? '/ask' : '/login'}>Ask Question</AskQuestionButton>          
          </AllQuestions>
          <At>
            <Date>Asked {timeForToday(createDate)}</Date>
            <Date>Motified {timeForToday(modifiedDate)}</Date>
            <Date>Viewed {question && question.data.views}</Date>
          </At>
          <QuestionContentView>
          <div style={{"display" : "flex", "margin" : "25px"}}>
            <Vote>
              <button style={{"backgroundColor" : "white", "border" : "none"}} onClick = {questionUpVoteHandler}>
                <CaretUpOutlined style={{"fontSize" : "30px"}}/>
              </button>
              <QuestionEstimation>{question && question.data.votes}</QuestionEstimation>
              <button style={{"backgroundColor" : "white", "border" : "none"}} onClick = {questionDownVoteHandler}>
                <CaretDownOutlined style={{"fontSize" : "30px"}}/>
              </button>
            </Vote>
          </div>
          <Content>
            <span dangerouslySetInnerHTML={{__html: question && question.data.content}} />
          </Content>
          </QuestionContentView>
        </Post>
        <Writer>
          <div className="createUser">
            <Date>Asked {timeForToday(createDate)}</Date>
            <img style={{"width": "30px"}} src={question && question.data.member.profileImageSrc} alt="유저 이미지" />
            <HyperLink to={question && `/member/${question.data.member.memberId}`} style={{"fontSize" : "15px"}}>{question && question.data.member.nickName}</HyperLink>
            <div>{question && question.data.member.memberId == userId ? <Link className="edit" to={question && `/askEdit/${id}`}>질문 수정</Link> : null}</div>
          </div>
        </Writer>
      </div>
      <YourAnswer>
        <ReactQuill 
          theme="snow"
          value={value}
          onChange={setValue}
          modules={modules}
          style={{ "height" : "400px"}}/>
      </YourAnswer>
      {isLogin === "true" ? 
        <PostAnswer>
          <SubmitButton onClick={handleSubmit}>Post Your Answer</SubmitButton>
            <span style={{"margin" : "18px", "fontSize" : "17px"}}>Not the answer you're looking for? 
            <HyperLink to = "/ask"> ask your own question</HyperLink></span>
        </PostAnswer> 
        :
        <></>
      }
      {isLogin === "true" ? <></> 
          :
          <Fragment>
            <FlexRight>
              <div style={{"whiteSpace": "nowrap"}} >
                <HyperLink to="/signup"> Signup </HyperLink>or
                <HyperLink to="/login"> login</HyperLink>
              </div>
              <div className='button-group'>
                <OAuthButton color="hsl(210deg 8% 20%)" bg_color="hsl(0deg 0% 100%)" hv_color="hsl(210deg 8% 98%)" ac_color="hsl(210deg 8% 95%)">
                  <Google /> 
                  Sign up with Google
                </OAuthButton>
                <OAuthButton color="#fff" bg_color="hsl(210deg 8% 20%)" hv_color="hsl(210deg 8% 15%)" ac_color="hsl(210deg 8% 5%)">
                  <GitHub /> 
                  Sign up with GitHub
                </OAuthButton>
                <Link to="/signup">
                  <OAuthButton color="#fff" bg_color="#385499" hv_color="#314a86" ac_color="hsl(205deg 46% 32%)">
                    <StackLogo><Logo style={{"height" : "20px"}}/></StackLogo>
                    Sign up using Email and Password
                  </OAuthButton>
                </Link>
              </div>
            </FlexRight>
        </Fragment>
      }
      <Answer>
        {question ? question.data.answers.length : 0} answers
        <AnswerContentView>
          {question && question.data.answers.map(answer =>
          <div className="eachAnswer" key={answer.answerId}>
            <div style={{"display" : "flex"}} key={answer.id}>
              <Vote>
                <button style={{"backgroundColor" : "white", "border" : "none"}} onClick = {() => answerUpVoteHandler(answer.answerId)}>
                  <CaretUpOutlined style={{"fontSize" : "30px"}}/>
                </button>
                <QuestionEstimation>{answer.votes}</QuestionEstimation>
                <button style={{"backgroundColor" : "white", "border" : "none"}} onClick = {() => answerDownVoteHandler(answer.answerId)}>
                  <CaretDownOutlined style={{"fontSize" : "30px"}}/>
                </button>
                <AdoptStyle style={answer.adopt === true ? {"color" : "blue"}: {"color" : "black"}}/>
              </Vote>
              {answerEdit && answer.member.memberId == userId ? 
                <div className="answer-edit">
                  <ReactQuill 
                  theme="snow"
                  defaultValue={answer.content}
                  onChange={setAnswerContent}
                  style={{"height" : "200px", "width" : "1034px"}}/>
                  <button className='edit-button' onClick={() => onAnswerEditHandler(answer.answerId)}>수정</button>
                </div>
                :
                <Content>
                  <div style={{"marginLeft" : "20px"}} dangerouslySetInnerHTML={{__html: answer.content}} />
                </Content>}
              </div>
              <Writer>
                <div className="createUser">
                  <Date>answered {timeForToday(answerCreateDate)}</Date>
                  <img style={{"width": "25px"}} src={question && answer.member.profileImageSrc} alt="유저 이미지"/>
                  <HyperLink to={`/member/${answer.member.memberId}`} style={{"fontSize" : "15px"}}>{question && answer.member.nickName}</HyperLink>
                  {question && answer.member.memberId == userId && answerEdit === false ? <p className="edit" onClick={answerEditStateChanger}>답변 수정</p> : null}
                </div>
              </Writer>
          </div>
          )}
        </AnswerContentView>
      </Answer>
    </All>
  )
}
export default QuestionDetail;