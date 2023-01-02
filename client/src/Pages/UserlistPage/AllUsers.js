import { useState } from "react";
import styled from "styled-components";
import useFetch from "../../Components/util/useFetch";
import { Link } from 'react-router-dom';
import Pagenation from './Pagenation';

export const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid hsl(210,8%,85%);
    padding: 40px;
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

const AllUser = styled.h2`
    font-size: 50px;
    font-weight: 500;
`
const UserSearch = styled.input`
    height: 40px;
    width: 220px;
    padding: 13px;
    font-size: 15px;
    margin-top: 20px;
    border-radius: 3px;
`;
const List = styled.div`
display: flex;
flex-wrap: wrap;
padding-top: 50px;
width: 1200px;
`;

const UserContainer = styled.div`
margin: 10px;
display: flex;
margin-right: 30px;
margin-bottom: 80px;
width: 230px;
`;

const LeftContainer = styled.img`
width: 60px;
height: 60px;
margin-left: 10px; 
margin-right: 10px;
border-radius: 3px;
`;

const RightContainer = styled.div`
display: flex;
flex-direction: column;
`;

const UserLink = styled(Link)`
text-decoration: none;
font-size: 20px;
color: hsl(206deg 100% 52%);
`;

const QuestionNumber = styled.div`
font-size: 18px;
color: black;
`;

const AllUsers = () => {
    const [limit, setLimit] = useState(localStorage.getItem("userSize") ? localStorage.getItem("userSize"): 15);
    const [page, setPage] = useState(localStorage.getItem("userPage")? localStorage.getItem("userPage") : 1);
    const [question, setQuestion] = useFetch(`http://13.125.30.88:8080/members?page=${page}`);
    console.log(question)

    const [search, setSearch] = useState("");

    const onChange = (e) => {
        setSearch(e.target.value);
    };

    const userFind = (e) => {
        if(e.key === "Enter") {
        setQuestion({...question, data: question.data.filter((el) => {return el.nickName.includes(search)}) })
        }
    }

    return(
        <All>
            <AllUser>All Users</AllUser>
            <UserSearch
                type="text"
                placeholder="Search..."
                onChange={onChange}
                onKeyDown={(e) => userFind(e)}
                />
                <List>
                    {question && question.data.map(user => 
                        <UserContainer key={user.memberId}>
                                <LeftContainer src={user.profileImageSrc}/>
                        <RightContainer>
                                <UserLink to={`/member/${user.memberId}`}>{user.nickName}</UserLink>
                                <QuestionNumber>{user.answers ? user.answers.length : '0'} Post</QuestionNumber>
                        </RightContainer>
                        </UserContainer>
                    )}
                </List>
                <label>
                    contents:&nbsp;
                    <select
                    type="number"
                    value={limit}
                    onChange={({ target: { value } }) => setLimit(Number(value), localStorage.setItem("userSize", value))}
                    >
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="30">30</option>
                    <option value="50">50</option>
                    </select>
                </label>
                {question && <Pagenation 
                    total={question.pageInfo.totalElements}
                    limit={limit}
                    page={page}
                    setPage={setPage}
                />}
        </All>
    )
};

export default AllUsers;