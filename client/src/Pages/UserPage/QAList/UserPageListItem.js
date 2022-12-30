import { NavLink } from 'react-router-dom'
import styled from 'styled-components';

const ItemContainer = styled.div `
    display: flex;
    border-top: 1px solid black;
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
    return (
        <ItemContainer key={question.answerId}>
            <Content >
                <span className='vote-number'>{question.votes} <span className='vote-unit'> votes</span> </span>
                <Detail to={`/questions/${question.questionId}`}>
                    <ContentsTitle>{question.subject}</ContentsTitle><br/>
                </Detail>
                <span className='created-at'>{question.createdAt}</span>
            </Content>
        </ItemContainer>
    )
}

export default UserPageListItem;