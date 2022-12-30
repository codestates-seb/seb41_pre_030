import ReactPaginate from 'react-paginate';
import { Fragment, useState } from "react";
import { Contents, ContentsTitle, Count, Detail, Question, QuestionCount, Questions } from './Home';
import styled from 'styled-components';

const Pagenation = (props) => {
    const [itemOffset, setItemOffset] = useState(0);

    const endOffset = itemOffset + props.itemsPerPage;
    console.log(`Loading items from ${itemOffset} to ${endOffset}`);
    const currentItems = props.questions.slice(itemOffset, endOffset);
    const pageCount = Math.ceil(props.questions.length / props.itemsPerPage);

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


    const handlePageClick = (event) => {
        const newOffset = (event.selected * props.itemsPerPage) % props.questions.length;
        console.log(
        `User requested page number ${event.selected}, which is offset ${newOffset}`
        );
        setItemOffset(newOffset);
    };

    return (
        <Fragment>
            {currentItems && currentItems.map(question => 
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
                        <img src={question.member.profileImageSrc} />
                        <span>{question.member.nickName}</span>
                    </UserBox>
                </Questions>
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

export default Pagenation;