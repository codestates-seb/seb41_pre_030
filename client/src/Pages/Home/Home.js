import React from "react"
import { NavLink } from 'react-router-dom'
import styled from "styled-components"
import Pagenation from './Pagenation'

export const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid hsl(210,8%,85%);
    display: grid;
    ul {
        display: flex;
        flex-direction: row;
        justify-content: center;
        list-style: none;
        margin-bottom: 30px;
        li {
            font-size: 18px;
            width: 30px;
            height: 27px;
            margin: 0 2px 0 2px;
            padding: 2px 0 0 0 ;
            text-align: center;
            border: 1px solid hsl(210,8%,85%);
            border-radius: 3px;
            cursor: pointer;
            :active {
                color: white;
                background: #F48225;
                border: 1px solid #F48225;
            }
        }
    }
`

const QuestionList = styled.div`
    font-size: 30px;
    width: 100vh;
    display: grid;
`

export const AllQuestions = styled.div`
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

export const AskQuestionButton = styled(NavLink)`
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

export const Questions = styled.div `
    display: flex;
    border-top: 1px solid hsl(210,8%,85%);;
`
export const QuestionCount = styled.div`
    font-size: 15px;
    color: gray;
    width: 150px;
    text-align: right;
    margin: 15px 0px 20px 10px;
`

export const Question = styled.div`
    width: 1000px;
    display: grid; 
    font-size: 20px;
    margin: 20px;
`
export const Count = styled.div`
    margin-left: 10px;
    margin-top: 20px;
`

export const Detail = styled(NavLink)`
    text-decoration: none;
    margin-top: 20px;
`

export const ContentsTitle = styled.span`
    font-size: 20px;
    color: hsl(206,100%,52%);;
    :hover {
        color: skyblue;
    }
`

export const Contents = styled.span`
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

const Home = ({questions}) => {
    return (
        <All>
            <QuestionList>
                <AllQuestions>
                    All Questions
                    <AskQuestionButton to='/ask'>Ask Question</AskQuestionButton>
                </AllQuestions>
                <CountQuestions>{questions && questions.length} questions</CountQuestions>
            </QuestionList>
            {questions && <Pagenation questions={questions.data} itemsPerPage={10} />}
        </All>
    )
}

export default Home;