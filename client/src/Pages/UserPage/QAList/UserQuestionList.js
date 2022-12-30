import { Fragment } from 'react';
import { useLocation } from 'react-router-dom';
import useFetch from '../../../Components/util/useFetch';
import UserPageListItem from './UserPageListItem';

const UserQuestionList = () => {
    const location = useLocation();
    const [question] = useFetch(`http://13.125.30.88:8080/members/${location.pathname.slice(8)}`);
    console.log(question)
    return (
        <Fragment>
            {question && <h2>{question.data.questions.length} Questions</h2>}
            {question && question.data.questions.map(question => 
                <UserPageListItem key={question.questionId} question={question}/>
            )}
        </Fragment>
    )
}

export default UserQuestionList;