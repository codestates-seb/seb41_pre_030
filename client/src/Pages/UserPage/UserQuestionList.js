import { Fragment, useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom'
import useFetch from '../../Components/util/useFetch';
import styled from 'styled-components';

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

const UserQuestionList = () => {
    const [question] = useFetch('http://localhost:3001/questions/')
    return (
        <Fragment>
            <h2>0 Questions</h2>
            {question && question.map(question => 
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
        </Fragment>
    )
}

export default UserQuestionList;