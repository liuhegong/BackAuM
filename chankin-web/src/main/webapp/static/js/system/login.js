$(document).ready(function () {
    var captcha;
    handlerEmbed = function (captchaObj) {
        $("#submit2").click(function (e) {
            var result = captchaObj.getValidate();
            if (!result) {
                $("#notice2").show();
                setTimeout(function () {
                    $("#notice2").hide();
                }, 2000);
            } else {
                $.ajax({
                    url: getRootPath() + "/system/captcha" + (new Date()).getTime(),
                    type: 'get',
                    dataType: 'json',
                    data: {
                        loginName: $('#username').val(),
                        password: $('#password').val(),
                        geetest_challenge: result.geetest_challenge,
                        geetest_validate: result.geetest_validate,
                        geetest_seccode: result.geetest_seccode
                    }
                })
            }
            e.preventDefault();
        });
        captchaObj.appendTo("#embed-captcha");
        captchaObj.onReady(function () {
            $("#wait2").hide();
        });
        captcha = captchaObj;
    };

    $.ajax({
        url: getRootPath() + "/system/captcha?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            // 调用 initGeetest 初始化参数
            // 参数1：配置参数
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "popup", // 产品形式，包括：float，popup
                width: "100%"
                // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
            }, handlerEmbed)
        }
    });

    $("#login").dialog({
        title: '系统登录',
        closable: false,
        width: 500,
        height: 300,
        cache: false,
        modal: true,
        datagrid: true,
        resizable: false,
        draggable: false,
        buttons: [
            {
                text: '登录',
                width: 100,
                handler: function () {
                    if (!$("#username").validatebox("isValid")) {
                        $("#username").focus();
                    } else if (!$("#password").validatebox("isValid")) {
                        $("#password").focus();
                    } else if (!captcha.getValidate()) {
                        common_tool.messager_show("请点击验证码!")
                    } else {
                        $.ajax({
                            url: getRootPath() + "/system/login",
                            type: "post",
                            dataType: "json",
                            data: {
                                loginName: $("#username").val(),
                                password: $("#password").val(),
                                platform: 1,
                                geetest_challenge: $("input[name='geetest_challenge']").val(),
                                geetest_validate: $("input[name='geetest_validate']").val(),
                                geetest_seccode: $("input[name='geetest_seccode']").val()
                            },
                            success: function (data) {
                                if (data.code == 10000) {
                                    location.href = getRootPath() + "/system/welcome"
                                } else if (data.code == 20001) {
                                    common_tool.messager_show(data.msg + '<br>' + '验证无法自动刷新,请手动');
                                    $("#username").focus();
                                } else if (data.code == 20003) {
                                    common_tool.messager_show(data.msg + '<br>' + '验证无法自动刷新,请手动');
                                    handlerEmbed.reset();
                                    $("#password").focus();

                                }
                                else {
                                    common_tool.messager_show(data.msg + '<br>' + '验证无法自动刷新,请手动');
                                }
                            }
                        })
                    }
                }
            }
        ]
    });

    $('#username').validatebox({
        required: true,
        missingMessage: '请输入账号'
    });
    $('#password').validatebox({
        required: true,
        validType: 'length[6, 30]',
        missingMessage: '请输入密码',
        invalidMessage: '请输入6-30位密码'
    });
    if (!$("#username").validatebox("isValid")) {
        $("#username").focus();
    } else if (!$("#password").validatebox("isValid")) {
        $("#password").focus();
    }
    $("#github").click(function () {
        location = "https://github.com/login/oauth/authorize?client_id=f4b35940357e82596645&state=hunt_admin&redirect_uri=http://127.0.0.1:8080/oauth/github";
    });
});