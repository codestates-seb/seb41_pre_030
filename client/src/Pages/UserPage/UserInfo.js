import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import styled from 'styled-components';
import useFetch from '../../Components/util/useFetch';
import profileImage from "../../Image/profile.png";
import store from '../../Redux/store';

const TopViewContainer = styled.div`
    position: relative;
    margin-bottom: 16px;
`
const TopView = styled.div`
    display: flex !important;
    align-items: center !important;
    flex-wrap: wrap !important;
    margin: -8px;
`
const TopViewImg = styled.a`
    margin: 8px;
    text-decoration: none;
    cursor: pointer;
    user-select: auto;
    div {
        box-shadow:  0 1px 2px hsla(0,0%,0%,0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05), 0 2px 8px hsla(0, 0%, 0%, 0.05);
        border-radius: 5px !important;
    }
    div img {
        width: 128px;
        height: 128px;
    }
`
const TopViewInfo = styled.div`
    margin: 8px;
    div {
        margin: 4px;
        line-height: 1px !important;
        font-size: 34px !important;
        margin-bottom: 12px;
    }
    ul {
        display: flex !important;
        list-style: none;
        color: hsl(210,8%,45%) !important;
        flex-wrap: wrap !important;
    }
    ul li {
        margin: 4px;
    }
`
const EditProfile = styled.div`
    position: absolute;
    display: flex !important;
    margin: -6px;
    left: 150px;
    bottom: 13px;
    a {
        padding: 5px;
        color: rgb(106, 115, 124);
        border: 1px solid hsl(210,8%,95%);
        text-decoration: none;
        background: none;
        border-radius: 5px !important;
        box-shadow:  0 1px 2px hsla(0,0%,0%,0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05), 0 2px 8px hsla(0, 0%, 0%, 0.05);
        border-radius: 5px !important;
        cursor: pointer;
    }
`

const UserInfo = () => {
    let param = useParams();
    const [question] = useFetch(`http://13.125.30.88:8080/members/${param}`);
    const [state, setState] = useState(store.getState());
    
    useEffect(() => {
        const unsubscribe = store.subscribe(() => {
            setState(store.getState())
        });
        return () => {
            unsubscribe()
        }
    }, [state])
    console.log(state)

    // let createDate = null;
    // let modifiedDate = null;
    // let answerCreateDate = null;

    // if (question) {
    //     createDate = new window.Date(question.data.createdAt)
    //     modifiedDate = new window.Date(question.data.modifiedAt)
    //     answerCreateDate = new window.Date(question.data.createdAt)
    // }

    // const timeForToday = (time) => {
    //     const today = new window.Date();
    //     const timeValue = new window.Date(time);
    //     const betweenTimeMin = Math.floor((today.getTime() - timeValue.getTime())/ 1000 / 60)
    //     const betweenTimeHour = Math.floor( betweenTimeMin / 60)
    //     const betweenTimeDay = Math.floor( betweenTimeMin / 60 / 24)
    
    //     if(betweenTimeMin < 1) return "방금 전"
    //     if(betweenTimeMin < 60) return `${betweenTimeMin} minute ago`
    //     if(betweenTimeHour < 24) return `${betweenTimeHour} hours ago`
    //     if(betweenTimeDay < 365) return `${betweenTimeDay} days ago`
    
    //     return `${Math.floor(betweenTimeDay / 365)} years ago`
    // }

    return (
        <TopViewContainer>
            <TopView>
                <TopViewImg>
                    <div>
                        {/* <img src={question.data.questionImageSrc} alt="user avatar"/> */}
                    </div>
                </TopViewImg>
                <TopViewInfo>
                    <div>
                        {/* {question.data.nickName} */}
                    </div>
                    <ul>
                        <li>🎂 Member for 0 days</li>
                        {/* <li>🎂 Member for {timeForToday(createDate)} days</li> */}
                        <li>🗓️ Visited 4 days</li>
                        {/* <li>🗓️ Visited {timeForToday(visitDate)} days</li> */}
                    </ul>
                </TopViewInfo>
                {/* {state.user에 멤버 아이디랑 url 아이디랑 같으면 에딧버튼 보여주기} */}
                <EditProfile> 
                    <Link to={'/member/edit'}>⚙️ Edit profile</Link>
                </EditProfile>
            </TopView>
        </TopViewContainer>
    )
}

export default UserInfo;