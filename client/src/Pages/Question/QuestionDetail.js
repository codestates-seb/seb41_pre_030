import React, { Fragment, useState } from "react";
import { All, AllQuestions, AskQuestionButton } from "../Home/Home"
import styled from "styled-components";
import useFetch from "../../Components/util/useFetch";
import { NavLink, useParams } from "react-router-dom";
import OAuthButton from "../Signup/OAuthButton";
import Google from "../../Image/Google";
import GitHub from "../../Image/GitHub";
import Logo from "../../Image/Logo";
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
const Content = styled.span`
  margin: 40px 10px 10px 0;
  font-size: 20px;
  min-height: 200px;
`

const QuestionContentView = styled.div`
  display: flex;
`

const AnswerContentView = styled.div`
  display: grid;
.eachAnswer{
  border-bottom: 1px solid gray;
  margin: 20px;
}
`

const Vote = styled.div`
  margin: 0px 20px;
  display: grid;
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
  margin: 90px 20px 20px 20px;
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
  border-radius: 5px;
  background-color: aliceblue;
  width: 170px;
  height: 100px;
  display: flex;
  float: right;
  margin: 0 10px 20px 0;
  .createUser{
    margin: 10px;
  }
`
const HyperLink = styled(NavLink)`
  text-decoration: none;
  color: blue
`

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
  //클라이언트가 서버에게 json 형식의 데이터가 보내지는 것인지 알려주는 설정


  //endpoint가 questionId가 되는 순간 데이터를 못 받아 옴
  const [question] = useFetch(`http://13.125.30.88:8080/questions/${id}`,request)

  const [value, setValue] = useState('');

  let isLogin = localStorage.getItem("isLogin");

  let createDate = null;
  let modifiedDate = null;
  let answerCreateDate = null;

  if (question) {
    createDate = new window.Date(question.data.createdAt)
    modifiedDate = new window.Date(question.data.modifiedAt)
    answerCreateDate = new window.Date(question.data.createdAt)
  }

  console.log(question)
  console.log(value)

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

  console.log(question.createdAt)

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

    const request = {
      method : "POST",
      body : JSON.stringify(value),
      header : {
        "Content-Type" : "application/json",
        "Authorization": localStorage.getItem("accessToken"),
        "Refresh": localStorage.getItem("refreshToken")
      }
    }
    fetch(`http://13.125.30.88:8080/questions/${id}/answers`, request)
    .then (() => {

      // window.location.reload()
    })
    .catch(err => {
      console.log(err)
    })
    console.log(e.target.value)
  }

  const downButton = () => {
    console.log("down")
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
            <AskQuestionButton to='/ask'>Ask Question</AskQuestionButton>          
          </AllQuestions>
          <At>
            <Date>Asked {timeForToday(createDate)}</Date>
            <Date>Motified {timeForToday(modifiedDate)}</Date>
            <Date>Viewed {question && question.data.views}</Date>
          </At>
          <QuestionContentView>
          <div style={{"display" : "flex", "margin" : "25px"}}>
            <Vote>
              <button style={{"backgroundColor" : "white", "border" : "none"}}>
                <CaretUpOutlined style={{"fontSize" : "30px"}}/>
              </button>
                  <QuestionEstimation>{question && question.data.votes }</QuestionEstimation>
              <button style={{"backgroundColor" : "white", "border" : "none"}}>
                <CaretDownOutlined style={{"fontSize" : "30px"}}/>
              </button>
            </Vote>
          </div>
          <Content>
            <span>{question && question.data.content}</span>
          </Content>
          </QuestionContentView>
        </Post>
        <Writer>
          <div className="createUser">
            <Date>Asked {timeForToday(createDate)}</Date>
            <img style={{"width": "25px"}} src={question && question.data.member.profileImageSrc}/>
            <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{question && question.data.member.nickName}</HyperLink>
          </div>
        </Writer>
      </div>
      <Answer>
        {question ? question.data.answers.length : 0} answers
        <AnswerContentView>
          {question && question.data.answers.map(answer =>
          <div className="eachAnswer" key={answer.answerId}>
            <div style={{"display" : "flex"}} key={answer.id}>
              <Vote>
                <button style={{"backgroundColor" : "white", "border" : "none"}}>
                  <CaretUpOutlined style={{"fontSize" : "30px"}}/>
                </button>
                <QuestionEstimation>{answer.votes}</QuestionEstimation>
                <button style={{"backgroundColor" : "white", "border" : "none"}}>
                  <CaretDownOutlined style={{"fontSize" : "30px"}} onClick = {downButton}/>
                </button>
                <AdoptStyle style={answer.adopt === true ? {"color" : "blue"}: {"color" : "black"}}/>
              </Vote>
              <Content>
                <div style={{"marginLeft" : "20px"}}>{answer.content}</div>
              </Content>
            </div>
            <Writer>
              <div className="createUser">
                <Date>answered {timeForToday(answerCreateDate)}</Date>
                <img style={{"width": "25px"}} src={question && answer.member.profileImageSrc} />
                <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{question && answer.member.nickName}</HyperLink>
              </div>
            </Writer>
          </div>
          )}
        </AnswerContentView>
      </Answer>
      <YourAnswer>
      <ReactQuill 
        theme="snow"
        value={value}
        onChange={setValue}
        modules={modules}
        style={{ "height" : "400px"}}/>
      </YourAnswer>

      {isLogin === "true" ? <></> :
          <Fragment>
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
                  <Logo style={{"height" : "1px"}}/> 
                  Sign up using Email and Password
                </OAuthButton>
              </div>
            </FlexRight>
        </Fragment>
      }
      <PostAnswer>
        <SubmitButton onClick={handleSubmit}>Post Your Answer</SubmitButton>
          <span style={{"margin" : "18px", "fontSize" : "17px"}}>Not the answer you're looking for? 
          <HyperLink to = "/ask"> ask your own question</HyperLink></span>
      </PostAnswer>
    </All>
  )
}
export default QuestionDetail

//css 정리, 답변 post 요청, click 할때마다 views + 1
