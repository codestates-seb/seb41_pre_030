import { Fragment } from 'react';
import useFetch from '../../../Components/util/useFetch';
import UserPageListItem from './UserPageListItem';

const UserQuestionList = () => {
    const [question] = useFetch('http://13.125.30.88:8080/questions/');
    
    return (
        <Fragment>
            {question && <h2>{question.filter(el => el.author === "John Doe").length} Questions</h2>}
            {question && question.filter((el => el.author === "John Doe")).map(question => 
                <UserPageListItem key={question.questionId} question={question}/>
            )}
        </Fragment>
    )
}

export default UserQuestionList;