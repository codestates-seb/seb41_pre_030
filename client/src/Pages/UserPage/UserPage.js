import styled from 'styled-components';
import UserQAList from './UserQAList';
import UserTopInfo from './UserTopInfo';

const Container = styled.div`
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
            <UserQAList />
        </Container>
    )
}

export default UserPage;