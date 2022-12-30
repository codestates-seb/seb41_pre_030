import { NavLink } from 'react-router-dom'
import styled from 'styled-components';

const ItemContainer = styled.div `
    display: flex;
    border-top: 1px solid black;
    align-items: center;
    button {
        border: none;
        background: none;
        color: red;
        width: 30px;
        height: 30px;
        cursor: pointer;
    }
`
const Content = styled.div`
    width: 1000px;
    position: relative;
    display: grid; 
    font-size: 20px;
    margin: 20px;
    .vote-number {
        font-size: 15px;
        font-weight: 500;
    }
    .created-at {
        position: absolute;
        font-size: 13px;
        color: rgb(106, 115, 124);
        right: 30px;
        bottom: 0;
    }
`
const Detail = styled(NavLink)`
    text-decoration: none;
    margin-top: 20px;
`
const ContentsTitle = styled.span`
    font-size: 16px;
    color: rgb(0, 116, 204);
    :hover {
        color: skyblue;
    }
`

const UserPageListItem = ({question}) => {
    let createDate = null;
    let modifiedDate = null;
    let answerCreateDate = null;

    if (question) {
      createDate = new window.Date(question.createdAt)
      modifiedDate = new window.Date(question.modifiedAt)
      answerCreateDate = new window.Date(question.createdAt)
    }

    const timeForToday = (time) => {
        const today = new window.Date();
        const timeValue = new window.Date(time);
        const betweenTimeMin = Math.floor((today.getTime() - timeValue.getTime())/ 1000 / 60)
        const betweenTimeHour = Math.floor( betweenTimeMin / 60)
        const betweenTimeDay = Math.floor( betweenTimeMin / 60 / 24)
    
        if(betweenTimeMin < 1) return "방금 전"
        if(betweenTimeMin < 60) return `${betweenTimeMin}분전`
        if(betweenTimeHour < 24) return `${betweenTimeHour} 시간 전`
        if(betweenTimeDay < 365) return `${betweenTimeDay} days ago`
    
        return `${Math.floor(betweenTimeDay / 365)} years ago`
    } 
    return (
        <ItemContainer key={question.answerId}>
            <Content >
                <span className='vote-number'>{question.votes} <span className='vote-unit'> votes</span> </span>
                <Detail to={`/questions/${question.questionId}`}>
                    <ContentsTitle>{question.subject}</ContentsTitle><br/>
                </Detail>
                <span className='created-at'>{timeForToday(createDate)}</span>
            </Content>
        </ItemContainer>
    )
}

export default UserPageListItem;