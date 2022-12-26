import React from "react"
import { NavLink } from 'react-router-dom'
import styled from "styled-components"

const All = styled.div`
    font-size: 30px;
    margin-left: 280px;
    width: 130vh;
    border-left: 1px solid black;
    display: grid;
`

const QuestionList = styled.div`
    font-size: 30px;
    width: 130vh;
    display: grid;
`

const AllQuestions = styled.div`
    width: 1100px;
    margin: 40px 40px 0px 40px;
    display: flex;
    justify-content: space-between;
`

const CountQuestions = styled.span`
    font-size: 20px;
    color: gray;
    margin-left: 40px;
    margin-bottom: 40px;
`

const AskQuestionButton = styled(NavLink)`
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

const Questions = styled.div `
    display: flex;
    border-top: 1px solid black;
`
const QuestionCount = styled.div`
    font-size: 15px;
    color: gray;
    width: 150px;
    text-align: right;
    margin: 15px 0px 20px 10px;
`

const Question = styled.div`
    width: 1000px;
    display: grid; 
    font-size: 20px;
    margin: 20px;

`
const Count = styled.div`
    margin-left: 10px;
    margin-top: 20px;
`

const Detail = styled(NavLink)`
    text-decoration: none;
    margin-top: 20px;
`

const ContentsTitle = styled.span`
    font-size: 20px;
    color: blue;
    :hover {
      color: skyblue;
    }
`

const Contents = styled.span`
    font-size: 18px;
    color: black;
    margin-top: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
`
const Pagenation = styled.span`
    background-color: aliceblue;

`

function Home ({questions}) {
    if(!questions) return null

   return (
       <All className='min-view'>
                <QuestionList>
                    <AllQuestions>
                        All Questions
                        <AskQuestionButton to='/AskQuestion'>Ask Question</AskQuestionButton>
                    </AllQuestions>
                    <CountQuestions>{questions.length} questions</CountQuestions>
                    {questions.map(question => 
                    <Questions>
                        <QuestionCount>
                            <Count>{question.vote} votes</Count>
                            <Count>{question.answer.length} answers</Count>
                            <Count>{question.view} views</Count>
                        </QuestionCount>
                        <Question key={question.questionId}>
                            <Detail to={`/questions/${question.questionId}`}>
                            <ContentsTitle>{question.subject}</ContentsTitle><br/>
                            </Detail>
                            <Contents>{question.content}</Contents>
                        </Question>
                    </Questions>
                    )}
                </QuestionList>
            <Pagenation>
                pagenation 1,2,3
            </Pagenation>
        </All>
   )

}

//pagenation, css- box안에 중간정렬, sidebar옆으로 이동할 때 고정 안되는 점, 글이 너무 길어지면 ...과 함께 생략하기(2줄...)
export default Home