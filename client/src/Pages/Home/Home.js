import React, { useState } from "react"
import { NavLink } from 'react-router-dom'
import styled from "styled-components"
import useFetch from '../../Components/util/useFetch'
import Pagenation from './Pagenation'

export const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid hsl(210,8%,85%);
    display: grid;
    padding-bottom: 50px;
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
            :hover {
                background-color: hsl(210,8%,85%)
            }
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
    max-height: 40px;
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
    height: 150px;
    display: flex;
    border-top: 1px solid hsl(210,8%,85%);;
    min-width: 1200px;
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
    height: 35px;
    text-overflow: ellipsis;
    white-space: pre-wrap;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    hyphens: auto !important;
    * {
        font-size: 15px;
        color: black;
        font-weight: normal;
    }
`
const UserBox = styled.div`
display: flex;
flex-grow: 1;
margin-top: auto;
justify-content: right;
width: 150px;
margin-bottom: 20px;
margin-right: 30px;
span{
    margin-top: auto;
    font-size: 15px;
}
img {
    margin-right: 10px;
    height: 25px;
    width : 25px;
    border-radius: 3px;
}
`

const Home = () => {
    const [limit, setLimit] = useState(localStorage.getItem("size") ? localStorage.getItem("size"): 10);
    const [page, setPage] = useState(localStorage.getItem("page")? localStorage.getItem("page") : 1);
    const [question] = useFetch(`http://13.125.30.88:8080/questions/?page=${page}&size=${limit}`)

    return (
        <All>
            <QuestionList>
                <AllQuestions>
                    All Questions
                    <AskQuestionButton to={localStorage.getItem("isLogin") ? '/ask' : '/login'}>Ask Question</AskQuestionButton>
                </AllQuestions>
                <CountQuestions>{question && question.pageInfo.totalElements} questions</CountQuestions>
            </QuestionList>
            {question &&  question.data && question.data.map(question => 
                <Questions key={question.questionId}>
                    <QuestionCount>
                        <Count>{question.votes} votes</Count>
                        <Count>{question.answerCount?question.answerCount : 0} answers</Count>
                        <Count>{question.views} views</Count>
                    </QuestionCount>
                    <Question>
                        <Detail to={`/questions/${question.questionId}`}>
                        <ContentsTitle>{question.subject}</ContentsTitle><br/>
                        </Detail>
                        <Contents dangerouslySetInnerHTML={{__html: question.content}}></Contents>
                    </Question>
                    <UserBox>
                        <img src={question.member.profileImageSrc} alt="유저 이미지" />
                        <span>{question.member.nickName}</span>
                    </UserBox>
                </Questions>
            )}
            <label>
                contents:&nbsp;
                <select
                type="number"
                value={limit}
                onChange={({ target: { value } }) => setLimit(Number(value), localStorage.setItem("size", value))}
                >
                <option value="10">10</option>
                <option value="15">15</option>
                <option value="30">30</option>
                <option value="50">50</option>
                </select>
            </label>
            {question &&  
            <Pagenation
                total={question.pageInfo.totalElements}
                limit={limit}
                page={page}
                setPage={setPage}
            />}
        </All>
    )
}

export default Home;