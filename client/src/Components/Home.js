import React from "react"
import { NavLink } from 'react-router-dom'
import styled from "styled-components"

//홈 필요 없을듯?
export const All = styled.div`
    font-size: 30px;
    margin-left: 280px;
    width: 130vh;
    border-left: 1px solid black;
    display: grid;
    min-height: 100vh;
`

const QuestionList = styled.div`
    font-size: 30px;
    width: 130vh;
    display: grid;
`

export const AllQuestions = styled.div`
    width: 1100px;
    margin: 40px 40px 0px 40px;
    display: flex;
    justify-content: space-between;
`

export const AskQuestionButton = styled(NavLink)`
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

function Home ({questions}) {
    if(!questions) return null

   return (
        <All>
            <QuestionList>
                <AllQuestions>
                    All Questions
                    <AskQuestionButton to='/AskQuestion'>Ask Question</AskQuestionButton>
                </AllQuestions>
                {/* <CountQuestions>{questions.length} questions</CountQuestions>
                {questions.map(question => 
                <Questions>
                    <QuestionCount>
                        <Count>{question.vote} votes</Count>
                        <Count>{question.answer.length} answers</Count>
                        <Count>{question.view} views</Count>
                    </QuestionCount>
                    <Question key={question.id}>
                        <Detail to={`/questions/${question.id}`}>
                        <ContentsTitle>{question.subject}</ContentsTitle><br/>
                        </Detail>
                        <Contents>{question.content}</Contents>
                        </Question>
                    </Questions>
                    )} */}
                </QuestionList>
            {/* <Pagenation>
                pagenation 1,2,3
            </Pagenation> */}
        </All>
   )

}

export default Home