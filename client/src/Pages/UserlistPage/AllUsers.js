import styled from "styled-components";
import UserList from "./UserList";

export const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid hsl(210,8%,85%);
    padding: 40px;
`

const AllUser = styled.h2`
    font-size: 50px;
    font-weight: 500;
`
const UserSearch = styled.input`
    height: 40px;
    width: 220px;
    padding: 13px;
    font-size: 15px;
    margin-top: 20px;
`;

const AllUsers = () => {
    return(
        <All>
            <AllUser>All Users</AllUser>
            <UserSearch placeholder="Filter by user" />
            <UserList />
        </All>
    )
};

export default AllUsers;