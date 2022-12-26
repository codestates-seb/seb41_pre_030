import ReactPaginate from 'react-paginate';
import { Fragment, useState } from "react";
import { Contents, ContentsTitle, Count, Detail, Question, QuestionCount, Questions } from './Home';

const Pagenation = (props) => {
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
            {currentItems && currentItems.map(question => 
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