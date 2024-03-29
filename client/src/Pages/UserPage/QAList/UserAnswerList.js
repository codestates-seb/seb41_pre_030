import { Fragment } from 'react';
import { useLocation } from 'react-router-dom';
import useFetch from '../../../Components/util/useFetch';
import UserPageListItem from './UserPageListItem';

const UserAnswerList = () => {
    const location = useLocation();
    const [question] = useFetch(`http://13.125.30.88:8080/members/${location.pathname.slice(8)}`);
    return (
        <Fragment>
            {question && <h2>{question.data.answers.length} Answers</h2>}
            {question && question.data.answers.map(answer => 
                <UserPageListItem key={answer.answerId} question={answer.question}/>
            )}
        </Fragment>
    )
}

export default UserAnswerList;