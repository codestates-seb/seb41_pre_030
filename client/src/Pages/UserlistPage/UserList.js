import React, { Fragment, useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import ReactPaginate from 'react-paginate';

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

const UserList = (props) => {
    const [itemOffset, setItemOffset] = useState(0);
    console.log(props.question)

    const endOffset = itemOffset + props.itemsPerPage;
    console.log(`Loading items from ${itemOffset} to ${endOffset}`);
    let currentItems = props.question.slice(itemOffset, endOffset);
    let pageCount = Math.ceil(props.question.length / props.itemsPerPage);
    console.log(props.question)

    const handlePageClick = (event) => {
        const newOffset = (event.selected * props.itemsPerPage) % props.question.length;
        console.log(
        `User requested page number ${event.selected}, which is offset ${newOffset}`
        );
        setItemOffset(newOffset);
    };

    return (
        <Fragment>
            <List>
                {currentItems && currentItems.map(user => 
                    <UserContainer key={user.memberId}>
                            <LeftContainer src={user.profileImageSrc}/>
                    <RightContainer>
                            <UserLink to={`/member/${user.memberId}`}>{user.nickName}</UserLink>
                            <QuestionNumber>{user.answers ? user.answers.length : '0'} Post</QuestionNumber>
                    </RightContainer>
                    </UserContainer>
                )}
            </List>
            {currentItems && 
            <ReactPaginate
                breakLabel="..."
                nextLabel=">"
                onPageChange={handlePageClick}
                pageRangeDisplayed={5}
                pageCount={pageCount}
                previousLabel="<"
                renderOnZeroPageCount={null}
            />}

        </Fragment>
    )
}

export default UserList;