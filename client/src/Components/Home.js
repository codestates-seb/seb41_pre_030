import React from "react"
import { Link, NavLink } from 'react-router-dom'
import styled from "styled-components"

const QuestionList = styled.div`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid black;
    display: grid;
`

const AllQuestions = styled.div`
    width: 1100px;
    margin: 40px;
    display: flex;
    justify-content: space-between;
`

const CountQuestions = styled.span`
    font-size: 20px;
    color: gray;
    margin-left: 40px;
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
    margin: 40px 0px;
   
    display: flex;

    border-top: 1px solid black;
`
const QuestionCount = styled.div`
    font-size: 15px;
    color: gray;
    margin-left:20px;
    width: 150px;
    text-align: right;
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
`

const ContentsTitle = styled.span`
    font-size: 20px;
    color: blue;
`

const Contents = styled.span`
    font-size: 18px;
    color: black;
    overflow:hidden; 
    text-overflow: ellipsis;
    
    `

function Home ({questions}) {
    if(!questions) return null

   return (
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
                        <Contents>{question.content}</Contents>
                        </Detail>
                    </Question>
                </Questions>
                )}
        </QuestionList>
   )

}
export default Home