import streamlit as st
import requests
import time

st.title("Email verification")

url_sending = "http://127.0.0.1:8080/email/sending"
url_verify = "http://127.0.0.1:8080/email"


# Constants
COUNTDOWN_TIME = 60*10  # seconds

with st.form("Email verification form"):
  email = st.text_input("Your email")
  verify = st.form_submit_button("📧 Verify Email")

  if verify:
    if email:
      payload = {
        "email": email,
      }
      try:
        response = requests.post(
          url_sending,
          json = payload,
          headers={"Content-Type": "application/json"}
        )
        if response.status_code==200:
          mes = response.text
          st.success(mes)
          col1, col2 = st.columns([4,2])
          with col1: code = st.text_input("Code", placeholder="Verified code", label_visibility="collapsed")
          with col2: verify_button = st.form_submit_button("Send code")
          # st.session_state.countdown_started = True
          # if st.session_state.countdown_started:
          #   place = st.empty()
          #   for remaining in range(COUNTDOWN_TIME, 0, -1):
          #     minutes = math.floor(remaining/60)
          #     seconds = remaining%60
          #     with place.container():
          #       st.warning(f"⏳ Time left: {minutes}:{seconds}")
          #     time.sleep(1)
          #   place.error("Your code is expired!!!")
          #   st.session_state.countdown_started = False
          if verify_button:
            if code:
                info = {
                  "email": email,
                  "code": int(code)
                }
                try:
                  res2 = requests.post(
                      url_verify,
                      json= info,
                      headers={"Content-type": "application/json"}
                  )
                  if res2.status_code==200:
                    # st.session_state.countdown_started = False
                    st.success(res2.text)
                except Exception as e:
                  st.error(e)
            else:
              st.warning("Please type your verified code")
      except Exception as e:
        st.error(f"Error: {e}")