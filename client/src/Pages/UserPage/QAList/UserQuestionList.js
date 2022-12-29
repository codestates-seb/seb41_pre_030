import { Fragment } from 'react';
import { useParams } from 'react-router-dom';
import useFetch from '../../../Components/util/useFetch';
import UserPageListItem from './UserPageListItem';

const UserQuestionList = () => {
    let param = useParams();
    const [question] = useFetch(`http://13.125.30.88:8080/members`);
    let idUser = [];
    if (question) {idUser = question.data.filter((el, idx) => el.memberId === param)};


    return (
        <Fragment>
            {idUser.questions && <h2>{question.questions.length} Questions</h2>}
            {idUser.questions && question.questions.map(question => 
                <UserPageListItem key={question.questionId} question={question}/>
            )}
        </Fragment>
    )
}

export default UserQuestionList;