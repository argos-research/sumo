function angle = getAngle(personID)
%getAngle Get the angle of the person.
%   angle = getAngle(PERSONID) Returns the angle in degrees of the named 
%   person within the last step. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAngle.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
angle = traci.person.getUniversal(constants.VAR_ANGLE, personID);