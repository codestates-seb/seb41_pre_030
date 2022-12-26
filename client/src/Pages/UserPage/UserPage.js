import { Route, Routes } from 'react-router-dom';
import styled from 'styled-components';
import UserInfoEdit from './UserInfoEdit';
import UserQAList from './QAList/UserQAList';
import UserTopInfo from './UserInfo';

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
    return (
        <Container>
            <UserTopInfo />
            <Routes>
                <Route path='*' element={<UserQAList />} />
                <Route path='/edit' element={<UserInfoEdit />} />
            </Routes>
        </Container>
    )
}

export default UserPage;