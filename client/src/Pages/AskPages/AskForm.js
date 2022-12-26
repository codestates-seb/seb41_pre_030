import styled from "styled-components";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import { useSelector } from 'react-redux'


const Container = styled.div`
	display: block;
	max-width: 1000px;
	padding: 30px 20px;
`;

const TopContainer = styled.div`
	background: #EBF4FB;
	border: 1px solid hsl(206deg 85% 57%);
	border-radius: 3px;
	display: block;
	width: 100%;
	padding: 25px;
	margin-bottom: 20px;

	ul {
		font-size: 16px;
		padding-left: 30px;
	}
`;

const SmallContainer = styled.div`
	border: 1px solid hsl(210deg 6% 72%);
	background: none;
	border-radius: 3px;
	display: block;
	width: 100%;
	padding: 25px;
	margin-bottom: 20px;
`;

const StepHeader = styled.h2`
	font-size: 1.3rem;
	margin-bottom: 20px;
`;

const InputHeader = styled.h3`
	font-size: 1.1rem;
`;

const InputHeaderDetail = styled.div`
	font-size: 0.9rem;
	font-weight: normal;
	margin-top: 10px;
`;

const QuestionTitleInput = styled.input`
	background: none;
	border: 1px solid hsl(210deg 6% 72%);
	border-radius: 3px;
	display: block;
	width: 100%;
	padding: 10px;
`;

const QuestionTextares = styled(ReactQuill)`
	background: none;
	width: 100%;

	.ql-editor {
		min-height: 300px;
	}
`;

const Button = styled.button`
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;

	display: inline-block;
	height: 36px;
	padding: 0px 15px;
	width: auto;

	border: 1px solid hsl(205deg 41% 63%);
	border-radius: 4px;
	font-weight: 500;
	background-color: hsl(206deg 100% 52%);
	color: #fff;

	:hover {
		background: hsl(209deg 100% 38%);
	}
	:active {
		background: hsl(209deg 100% 32%);
	}
	cursor: pointer;
`;

const AskPage = () => {

	// const user = useSelector();

	const toolbarOptions = [
		["bold", "italic", "underline"], // toggled buttons
		["blockquote", "code-block"],

		[{ list: "ordered" }, { list: "bullet" }], // superscript/subscript

		[{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown

		["link", "image", "video"]
		];

	const modules = {
		toolbar: toolbarOptions,
	};

	const [title, setTitle] = useState("");
	const [body, setBody] = useState("");
	const navigate = useNavigate();

	const handleQuill = (value) => {
		setBody(value);
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		if (title !== "" && body !== "") {
			const bodyJSON = {
				title: title,
				body: body,
				// user: user,
			};

			await axios
				.post("/api", bodyJSON)
				.then((res) => {
					// console.log(res.data);
					alert("Question added successfully");
					navigate.push("/");
			})

			.catch((err) => {
				console.log(err);
			});
		}
	}
	return (
		<Container>
			<TopContainer>
			<StepHeader>Writing a good question</StepHeader>
				<InputHeader>
					Steps
					<InputHeaderDetail>
					<ul>
						<li>Summarize your problem in a one-line title.</li>
						<li>Describe your problem in more detail.</li>
						<li>Describe what you tried and what you expected to happen.</li>
						<li>Review your question and post it to the site.</li>
					</ul>
					</InputHeaderDetail>
				</InputHeader>
			</TopContainer>
			<SmallContainer>
				<InputHeader>Title</InputHeader>
				<InputHeaderDetail>
					Be specific and imagine you’re asking a question to another person.
				</InputHeaderDetail>
				<QuestionTitleInput
					value={title}
					onChange={(e) => setTitle(e.target.value)}
					type="text"
					placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
				/>
			</SmallContainer>
			<SmallContainer>
				<QuestionTextares 
					value={body}
					onChange={handleQuill}
					modules={modules}
					className="react-quill"
					theme="snow"/>
			</SmallContainer>
			<Button onClick={handleSubmit} className="button">Post your Question</Button>
		</Container>
	);
	};

export default AskPage;
