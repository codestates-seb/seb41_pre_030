import { useRef, useState } from 'react';
import styled from 'styled-components';
import profileImage from "../../Image/profile.png";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


const Container = styled.div`
    position: absolute;
    width: 1100px;
    button {
        position: absolute;
        bottom: 40px;
        right: 180px;
        display: inline-block;
        background-color: hsl(206deg 100% 52%);
        color: #fff;
        height: 36px;
        padding: 0px 15px;
        margin-top: 8px;
        width: auto;
        border: 1px solid hsl(205deg 41% 63%);
        border-radius: 4px;
        font-size: 20px;
        font-weight: 500;
        cursor: pointer;
    }
    .delete-account {
        right: 0;
        border: 1px solid #d0394e;
        background-color: #d0393e;
    }
    span {
        display: inline-block;
        color: red;
        font-size: 20px;
        font-weight: bold;
        margin-top: 20px;
    }
`
const EditTitle = styled.div`
    display: flex;
    flex-direction: row;
    align-items:  flex-end;
    margin:24px 0 24px 0 !important;
    padding-bottom: 16px;
    border-bottom: 1px solid hsl(210,8%,85%);
    justify-content: space-between;
    width: 100%;
    h1 {
        font-weight: 400;
    }
`
const Form = styled.form`
    position: relative;
    height: 600px;
`
const ImageContainer = styled.div`
    display: flex;
    flex-direction: column;
    div {
        width: 200px;
        height: 200px;
        border-radius: 5px 5px 0 0 !important;
    }
    div img {
        width: 100%;
        height: 200px;
        cursor: pointer;
    }
    label {
        background-color: gray;
        border-radius: 0 0 5px 5px !important;
        color: white;
        padding-top: 5px;
        width: 200px;
        height: 30px;
        text-align: center;
        cursor: pointer;
    }
`
const InputCotainer = styled.div`
    display: flex;
    flex-direction: column;
    padding-top: 20px;
    label {
        font-size: 20px;
        font-weight: 600;
        margin: 5px;
        cursor: pointer;
    }
    input {
        max-width: 400px;
        padding: 8px 10px 8px 10px;
        font-size: 20px;
        border: 1px solid rgb(186, 191, 196);
        border-radius: 3px;
    }
`

const UserInfoEdit = ({question}) => {
    const [image, setImage] = useState(question.data.profileImageSrc)
    const [nickname, setNickname] = useState(question.data.nickName)
    const fileInput = useRef(null)
    const navigate = useNavigate();

    const onImageReviewHandler = async (e) => {
        if(!e.target.files[0]){
            setImage(profileImage);
            return;
        }

        const reader = new FileReader();
        reader.onload = () => {
            if(reader.readyState === 2){
                setImage(reader.result)
            }
        }
        reader.readAsDataURL(e.target.files[0])
        const formData = new FormData();
        formData.append("file", fileInput.current.files[0]);
        await axios({
            method: "POST",
            url: `http://13.125.30.88:8080/files`,
            headers: {
                "Content-Type": "multipart/form-data", // Content-Type을 반드시 이렇게 하여야 한다.
                "Authorization": localStorage.getItem("accessToken"),
				"Refresh": localStorage.getItem("refreshToken")
            },
            data: formData, // data 전송시에 반드시 생성되어 있는 formData 객체만 전송 하여야 한다.
        });
        navigate(`/member/${question.data.memberId}`)
        window.location.reload();
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        const data = JSON.stringify({
            memberId : ''+question.data.memberId,
            email : question.data.email,
            nickName : nickname,
            memberStatus : question.data.memberStatus
        })
        console.log(data)
        await axios({
            method: "PATCH",
            url: `http://13.125.30.88:8080/members/${question.data.memberId}`,
            headers: {
                "Content-Type": 'application/json',
                "Authorization": localStorage.getItem("accessToken"),
                "Refresh": localStorage.getItem("refreshToken")
            },
            data: data
        });
        navigate(`/member/${question.data.memberId}`)
        window.location.reload();
    }

    const onDeleteAccountHandler = async () => {
        await axios({
            method: "DELETE",
            url: `http://13.125.30.88:8080/members/${question.data.memberId}`,
            headers: {
                "Authorization": localStorage.getItem("accessToken"),
                "Refresh": localStorage.getItem("refreshToken")
            },
            }).then(data => {
            
            })
            .catch(err => {
                console.log("err")
                return
            })
        localStorage.clear();
        window.location.reload();
        navigate(`/`);
        window.location.reload();
    }

    return (
        <Container>
            <span>이 페이지에서 새로고침을 금지합니다.</span>
            <EditTitle>
                <h1>Edit your profile</h1>
            </EditTitle>
            <Form onSubmit={onSubmitHandler}>
                <ImageContainer>
                    <div>
                        <img src={image} alt="user avatar" onClick={()=>{fileInput.current.click()}}/>
                    </div>
                    <label htmlFor='profile_img'>Change Picture</label>
                    <input
                        type='file' 
                        id='profile_img'
                        name='profile_img'
                        style={{display:'none'}}
                        accept="image/jpg, image/png, image/jpeg"
                        onChange={(e)=>onImageReviewHandler(e)}
                        ref={fileInput}
                    />
                </ImageContainer>
                <InputCotainer>
                    <label htmlFor='nickname'>Nickname</label>
                    <input
                        value={nickname}
                        type='text' 
                        id='nickname'
                        name='nickname'
                        onChange={event => setNickname(event.target.value)}
                        // ref={nicknameInput}
                    />
                </InputCotainer>
                <button>Edit profile</button>
            </Form>
            <button className='delete-account' onClick={onDeleteAccountHandler}>Delete profile</button>
        </Container>
    )
}

export default UserInfoEdit;