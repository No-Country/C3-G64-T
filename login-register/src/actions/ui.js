import { types } from "../types/types";

export const setUiError = (e) => ({
  type: types.setUiError,
  payload: e,
});

export const removeUiError = () => ({
  type: types.removeUiError,
});