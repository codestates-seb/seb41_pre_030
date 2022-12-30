import { Route, Routes, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import UserInfoEdit from './UserInfoEdit';
import UserQAList from './QAList/UserQAList';
import UserInfo from './UserInfo';
import useFetch from '../../Components/util/useFetch';
import { useEffect } from 'react';

const Container = styled.div`
    min-height: 900px;
    max-width: 1100px;
    width: calc(100% - 164px);
    background-color: white;
    border: 1px solid hsl(210,8%,85%);
    border-width: 0 0 0 1px;
    margin-left: 280px;
    padding: 24px;
    h2 {
        margin-bottom: 10px;
        font-weight: 400;
    }
`

const UserPage = () => {
    const location = useLocation();
    const id = location.pathname.slice(8,10)
    const [question] = useFetch(`http://13.125.30.88:8080/members/${id}`);

    useEffect(() => {
    }, [])
    
    return (
        <Container>
            <UserInfo id={id} question={question}/>
            <Routes>
                <Route path='*' element={<UserQAList />} />
                <Route path={`:id/edit/`} element={<UserInfoEdit question={question}/>} />
            </Routes>
        </Container>
    )
}

export default UserPage;