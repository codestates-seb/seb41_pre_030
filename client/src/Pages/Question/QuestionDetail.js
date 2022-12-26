import React, { useState } from "react";
import { All, AllQuestions, AskQuestionButton } from "../Home/Home"
import styled from "styled-components";
import useFetch from "../../Components/util/useFetch";
import { NavLink, useParams } from "react-router-dom";
import OAuthButton from "../Signup/OAuthButton";
import Google from "../../Image/Google";
import GitHub from "../../Image/GitHub";
import Logo from "../../Image/Logo";
import { CaretUpOutlined, CaretDownOutlined } from '@ant-design/icons' ;
import ReactQuill from 'react-quill'
import 'react-quill/dist/quill.snow.css';
import { format } from 'date-fns'

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
  margin: 10px 0 10px 25px;
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
  margin: 20px;
  display: flex;
`

const FlexRight = styled.div`
  display: grid;
  margin: 50px 20px 20px 20px;
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
  width: 150px;
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

function QuestionDetail () {

  const { id } = useParams()
  const request = {
    method : "get",
    headers : {"Content-Type" : "application/json"}
  }
  //클라이언트가 서버에게 json 형식의 데이터가 보내지는 것인지 알려주는 설정

  const [question] = useFetch(`http://localhost:3001/questions/${id}`,request)
  const [member] = useFetch(`http://localhost:3001/members/${id}`,request)

  const [value, setValue] = useState('');

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

const PlusGood = () => {
  const [good, setgood] = useState(question.vote)
}

  return(
    <All>
      <div>
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
          <div style={{"display" : "flex", "margin" : "25px"}}>
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
        </Post>
        <Writer>
          <div className="createUser">
            <Date>Asked {question.createdAt}</Date>
            <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{member.nickName}</HyperLink>
          </div>
        </Writer>
      </div>
      <Answer>
        <AnswerCount>{question && question.answer.length} answers</AnswerCount>
        <AnswerContentView>
          {question && question.answer.map(answer =>
          <div className="eachAnswer">
            <div style={{"display" : "flex"}} key={answer.id}>
              <Vote>
                <button style={{"backgroundColor" : "white", "border" : "none"}} onClick={PlusGood}>
                  <CaretUpOutlined style={{"fontSize" : "30px"}}/>
                </button>
                <QuestionEstimation>{answer.vote}</QuestionEstimation>
                <button style={{"backgroundColor" : "white", "border" : "none"}}>
                  <CaretDownOutlined style={{"fontSize" : "30px"}}/>
                </button>
              </Vote>
              <Content>
                <div style={{"marginLeft" : "20px"}}>{answer.content}</div>
              </Content>
            </div>
            <Writer>
              <div className="createUser">
                <Date>answered {question.createdAt}</Date>
                <HyperLink to="/UserPage" style={{"fontSize" : "15px"}}>{member.nickName}</HyperLink>
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

      {/*로그인 시 뜨지 않게 해야함 */}
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
        <SubmitButton>Post Your Answer</SubmitButton>
          <span style={{"margin" : "18px", "fontSize" : "17px"}}>Not the answer you're looking for? 
          <HyperLink to = "/AskQuestion"> ask your own question</HyperLink></span>
        </PostAnswer>

    </All>
  )
}
export default QuestionDetail

//css 정리, d-day, 답변 post 요청, click 할때마다 views + 1
