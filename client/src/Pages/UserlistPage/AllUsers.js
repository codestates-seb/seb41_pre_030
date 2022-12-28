import styled from "styled-components";
import UserList from "./UserList";
import useFetch from "../../Components/util/useFetch";

export const All = styled.main`
    font-size: 30px;
    margin-left: 280px;
    width: 100vh;
    border-left: 1px solid hsl(210,8%,85%);
    padding: 40px;
    ul {
        display: flex;
        flex-direction: row;
        justify-content: center;
        list-style: none;
        margin-bottom: 30px;
        li {
            font-size: 18px;
            width: 30px;
            height: 27px;
            margin: 0 2px 0 2px;
            padding: 2px 0 0 0 ;
            text-align: center;
            border: 1px solid hsl(210,8%,85%);
            border-radius: 3px;
            cursor: pointer;
            :active {
                color: white;
                background: #F48225;
                border: 1px solid #F48225;
            }
        }
    }
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
    const [question] = useFetch('http://13.125.30.88:8080/members');

    return(
        <All>
            <AllUser>All Users</AllUser>
            <UserSearch placeholder="Filter by user" />
            {question && <UserList question={question} itemsPerPage={20} />}
        </All>
    )
};

export default AllUsers;