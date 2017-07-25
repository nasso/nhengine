uniform sampler2D color;
uniform vec4 vignetteColor;
uniform vec2 parameters; // x : dist | y : penumbra

in vec2 pass_quad_uv;

out vec4 out_color;

void main() {
	float minDist = parameters.x - parameters.y * 0.5;
	float maxDist = parameters.x + parameters.y * 0.5;
	
	float dist = distance(pass_quad_uv * 2.0 - 1.0, vec2(0.0, 0.0));
	
	if(dist < minDist) {
		out_color = texture(color, pass_quad_uv).xyzw;
		return;
	} else if(dist < maxDist || vignetteColor.a != 1.0) {
		out_color = mix(texture(color, pass_quad_uv).xyzw, vec4(vignetteColor.xyz, 0.0), smoothstep(minDist, maxDist, dist) * vignetteColor.a);
		return;
	}
	
	out_color = vec4(vignetteColor.rgb, 0.0);
}