import React, { Fragment, useState } from "react";
import profileImage from "../../Image/profile.png";
import styled from "styled-components";
import { Link } from "react-router-dom";
import ReactPaginate from 'react-paginate';

const List = styled.div`
    display: grid;

    padding-top: 50px;
`;

const UserContainer = styled.div`
    display: flex;
`;

const LeftContainer = styled.img`
    width: 60px;
    height: 60px;
    margin-left: 10px; 
    margin-right: 10px;
`;

const RightContainer = styled.div`
    display: flex;
    flex-direction: column;
`;

const UserLink = styled(Link)`
    text-decoration: none;
    font-size: 25px;
    color: hsl(206deg 100% 52%);
`;

const QuestionNumber = styled.div`
    font-size: 18px;
    color: black;
`;

const UserList = (props) => {

    const [itemOffset, setItemOffset] = useState(0);

    const endOffset = itemOffset + props.itemsPerPage;
    console.log(`Loading items from ${itemOffset} to ${endOffset}`);
    const currentItems = props.questions.slice(itemOffset, endOffset);
    const pageCount = Math.ceil(props.questions.length / props.itemsPerPage);


    const handlePageClick = (event) => {
        const newOffset = (event.selected * props.itemsPerPage) % props.questions.length;
        console.log(
        `User requested page number ${event.selected}, which is offset ${newOffset}`
        );
        setItemOffset(newOffset);
    };

    return (
        <Fragment>
            {currentItems && currentItems.map(user => 
                <List>
                <UserContainer>
                        <LeftContainer src={profileImage}/>
                <RightContainer>
                        <UserLink to="/api">This is user className</UserLink>
                        <QuestionNumber>408</QuestionNumber>
                </RightContainer>
                </UserContainer>
                </List>
            )}
            <ReactPaginate
            breakLabel="..."
            nextLabel=">"
            onPageChange={handlePageClick}
            pageRangeDisplayed={5}
            pageCount={pageCount}
            previousLabel="<"
            renderOnZeroPageCount={null}
            />
        </Fragment>
    )
}

export default UserList;