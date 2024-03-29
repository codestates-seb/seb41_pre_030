import { Link } from 'react-router-dom';
import styled from 'styled-components';

const TopViewContainer = styled.div`
    position: relative;
    margin-bottom: 16px;
    min-height: 148.4px;
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

const UserInfo = ({ id, question }) => {
    let createDate = null;
    let modifiedDate = null;
    let user = localStorage.getItem("user")

    if (question) {
        createDate = new window.Date(question.data.createdAt)
        modifiedDate = new window.Date(question.data.modifiedAt)
    }

    const timeForToday = (time) => {
        const today = new window.Date();
        const timeValue = new window.Date(time);
        const betweenTimeMin = Math.floor((today.getTime() - timeValue.getTime())/ 1000 / 60)
        const betweenTimeHour = Math.floor( betweenTimeMin / 60)
        const betweenTimeDay = Math.floor( betweenTimeMin / 60 / 24)
    
        if(betweenTimeMin < 1) return "방금 전"
        if(betweenTimeMin < 60) return `${betweenTimeMin} minute ago`
        if(betweenTimeHour < 24) return `${betweenTimeHour} hours ago`
        if(betweenTimeDay < 365) return `${betweenTimeDay} days ago`
    
        return `${Math.floor(betweenTimeDay / 365)} years ago`
    }

    return (
        <TopViewContainer>
            <TopView>
                <TopViewImg>
                    <div>
                        <img src={question && question.data.profileImageSrc} alt="user avatar"/>
                    </div>
                </TopViewImg>
                <TopViewInfo>
                    <div>
                        {question && question.data.nickName}
                    </div>
                    <ul>
                        <li>🎂 Member for {timeForToday(createDate)}</li>
                        <li>🗓️ Visited {timeForToday(modifiedDate)}</li>
                    </ul>
                </TopViewInfo>
                <EditProfile> 
                    {id == user && <Link to={`/member/${id}/edit`}>⚙️ Edit profile</Link>}
                </EditProfile>
            </TopView>
        </TopViewContainer>
    )
}

export default UserInfo;