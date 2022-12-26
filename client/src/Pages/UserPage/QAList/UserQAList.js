import { useState } from 'react';
import styled from 'styled-components';
import UserAnswerList from './UserAnswerList';
import UserQuestionList from './UserQuestionList';

const ButtonWrap = styled.div`
    display: flex !important;
    margin-bottom: 30px !important;
    align-items: center !important;
    text-align: left;
    justify-content: space-between !important;
    flex-wrap: wrap !important;
    gap: 4px;
    button {
        flex-direction: row;
        flex-wrap: wrap;
        font-size: 15px;
        padding: 6px 12px 6px 12px;
        background-color: white;
        border: none;
        border-radius: 1000px;
        cursor: pointer;
    }
    div {
        margin-left: auto !important; 
    }
    .active {
        background-color: rgb(244, 130, 37);
        color: white;
    }
`

const UserQAList = () => {
    const buttonArr = ['Question', 'Answer'];
    const [btnActive, setBtnActive] = useState('0');

    const onButtonActive = (e) => {
        setBtnActive(e.target.value);
    };

    return (
        <div>
            <ButtonWrap>
                {buttonArr.map((item, idx) => { return (
                    <button
                        key={idx}
                        value={idx}
                        className={'' + idx === btnActive ? "active" : ""} 
                        onClick={(e) => onButtonActive(e)}
                    >
                    {item}
                    </button>
                )})}
                <div></div>
            </ButtonWrap>
            <div>
                {btnActive === '0' ? <UserQuestionList /> : <UserAnswerList />}
            </div>
        </div>
    )
}

export default UserQAList;