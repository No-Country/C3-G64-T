import React from "react";
import { Link } from "react-router-dom";
import { useForm } from "../../hooks/useForm";
import validator from "validator";
import { useDispatch, useSelector } from "react-redux";
import { setUiError, removeUiError } from "../../actions/ui";

const RegisterScreen = () => {
  const initialUser = {
    nick: "Emc",
    name: "Albert",
    lastname: "Einstein",
    email: "mimail@gmail.com",
    password: "123456",
    password2: "123456",
    dni: 33333333,
    birthday: "1991-08-12",
    phone: "2618546239",
  };

  const [formValues, handleInputChange] = useForm(initialUser);

  const { nick, name, lastname, email, password, password2, dni, birthday, phone } = formValues;

  const dispatch = useDispatch();
  const { msgError } = useSelector((state) => state.ui);

  const handleRegister = (e) => {
    e.preventDefault();
    if (isFormValid()) {
      console.log("Formulario válido");
    }
  };

  const isFormValid = () => {

    if (name.trim().length < 2) {
      dispatch(setUiError("Please, introduce a complete name"));
      return false;
    } else if (lastname.trim().length < 2) {
      dispatch(setUiError("Please, introduce a complete surname"));
      return false;
    } else if (!validator.isEmail(email)) {
      dispatch(setUiError("Please, introduce a valid email"));
      return false;
    } else if (password !== password2 || password.length < 6) {
      dispatch(setUiError("Password must be at least 6 characters long"));
      return false;
    } else if (validator.isAfter(birthday)) {
      dispatch(setUiError("Date cannot be equal or greater than current day"));
      return false;
    } else if (validator.isEmpty(dni.toString(), { ignore_whitespace: true })) {
      dispatch(setUiError("ID muts be a valid combinaion of numbers"));
      return false;
    }

    dispatch(removeUiError());
    return true;
  };

  return (
    <>
      <form onSubmit={handleRegister}>
        <h3 className="auth__title mb-5">Register</h3>
        {msgError && <div className="auth__alert-error">{msgError}</div>}
        <input
          className="auth__input"
          type="text"
          placeholder="Nick"
          name="nick"
          autoComplete="off"
          onChange={handleInputChange}
          value= {nick}
          required
        />
        <input
          className="auth__input"
          type="text"
          placeholder="Name"
          name="name"
          autoComplete="off"
          onChange={handleInputChange}
          value= {name}
          required
        />
        <input
          className="auth__input"
          type="text"
          placeholder="LastName"
          name="lastname"
          autoComplete="off"
          onChange={handleInputChange}
          value= {lastname}
          required
        />
          <input
            className="auth__input"
            type="email"
            placeholder="Email"
            name="email"
            autoComplete="off"
            onChange={handleInputChange}
            value= {email}
            required
          />
        <input
          className="auth__input"
          type="password"
          placeholder="Password"
          name="password"
          onChange={handleInputChange}
          value= {password}
          required
        />
        <input
          className="auth__input"
          type="password"
          placeholder="Repeat your password"
          name= "password2"
          onChange={handleInputChange}
          value= {password2}
          required
        />
        <input
          className="auth__input"
          type="number"
          placeholder="DNI"
          name="dni"
          autoComplete="off"
          onChange={handleInputChange}
          value= {dni}
          required
        />
        <input
          className="auth__input"
          type="date"
          placeholder="Birthday"
          name="birthday"
          autoComplete="off"
          onChange={handleInputChange}
          value= {birthday}
          required
        />
        <input
          className="auth__input"
          type="tel"
          placeholder="Phone"
          name="phone"
          onChange={handleInputChange}
          value= {phone}
          required
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
