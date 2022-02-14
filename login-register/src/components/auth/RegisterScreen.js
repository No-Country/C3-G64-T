import React from "react";
import { Link } from "react-router-dom";
import { useForm } from "../../hooks/useForm";

const RegisterScreen = () => {
  const initialUser = {
    nick: "maxcal",
    name: "Maximiliano",
    lastname: "Calderón",
    email: "calderonmaxi@outlook.com",
    password: "1234",
    password2: "1234",
    dni: 36169378,
    birthday: "1991-08-12",
    phone: "2616531850",
  };

  const [formValues, handleInputChange] = useForm(initialUser);

  const { nick, name, lastname, email, password, password2, dni, birthday, phone } = formValues;

  const handleRegister = (e) => {
    e.preventDefault();
    console.log(nick, name, lastname, email, password, password2, dni, birthday, phone)
  };

  return (
    <>
      <form onSubmit={handleRegister}>
        <h3 className="auth__title mb-5">Register</h3>
        <input
          className="auth__input"
          type="text"
          placeholder="Nick"
          name="nick"
          autoComplete="off"
          onChange={handleInputChange}
          value= {nick}
        />
        <input
          className="auth__input"
          type="text"
          placeholder="Name"
          name="name"
          autoComplete="off"
          onChange={handleInputChange}
          value= {name}
        />
        <input
          className="auth__input"
          type="text"
          placeholder="LastName"
          name="lastname"
          autoComplete="off"
          onChange={handleInputChange}
          value= {lastname}
        />
          <input
            className="auth__input"
            type="text"
            placeholder="Email"
            name="email"
            autoComplete="off"
            onChange={handleInputChange}
            value= {email}
          />
        <input
          className="auth__input"
          type="password"
          placeholder="Password"
          name="password"
          onChange={handleInputChange}
          value= {password}
        />
        <input
          className="auth__input"
          type="password"
          placeholder="Repeat your password"
          name= "password2"
          onChange={handleInputChange}
          value= {password2}
        />
        <input
          className="auth__input"
          type="number"
          placeholder="DNI"
          name="dni"
          autoComplete="off"
          onChange={handleInputChange}
          value= {dni}
        />
        <input
          className="auth__input"
          type="date"
          placeholder="Birthday"
          name="birthday"
          autoComplete="off"
          onChange={handleInputChange}
          value= {birthday}
        />
        <input
          className="auth__input"
          type="number"
          placeholder="Phone"
          name="phone"
          onChange={handleInputChange}
          value= {phone}
        />

        <button className="btn btn-primary btn-block mb-5" type="submit" >
          Register
        </button>

        <Link className="link" to="/auth/login">
          Already registered?
        </Link>
      </form>
    </>
  );
};

export default RegisterScreen;
