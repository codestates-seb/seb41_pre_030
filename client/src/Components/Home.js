import React from "react"
import { NavLink } from 'react-router-dom'
import styled from "styled-components"

const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 130vh;
    border-left: 1px solid hsl(210,8%,85%);
    display: grid;
`

const QuestionList = styled.div`
    font-size: 30px;
    width: 100vh;
    display: grid;
`

const AllQuestions = styled.div`
    width: 1100px;
    height: 40px;
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
    width: 120px;
    height: 40px;
    padding-top: 10px;
    font-size: 17px;
    margin-top: 10px;
    text-align: center;
    text-decoration: none;
    border: 1px solid hsl(205deg 41% 63%);
    border-radius: 4px;
    font-weight: 500;
    background-color: hsl(206deg 100% 52%);
    color: #fff;
    :hover {
      background-color: hsl(209deg 100% 38%);
    }
`

const Questions = styled.div `
    display: flex;
    border-top: 1px solid hsl(210,8%,85%);;
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
    color: hsl(206,100%,52%);;
    :hover {
      color: skyblue;
    }
`

const Contents = styled.span`
    display: -webkit-box;
    font-size: 15px;
    color: black;
    height: 35px;
    text-overflow: ellipsis;
    white-space: pre-wrap;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    hyphens: auto !important;
`
const Pagenation = styled.span`
    background-color: aliceblue;
`

const Home = ({questions}) => {
    if(!questions) return null

   return (
       <All>
            <QuestionList>
                <AllQuestions>
                    All Questions
                    <AskQuestionButton to='/AskQuestion'>Ask Question</AskQuestionButton>
                </AllQuestions>
                <CountQuestions>{questions.length} questions</CountQuestions>
                {questions.map(question => 
                    <Questions key={question.id}>
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

//pagenation
export default Home;