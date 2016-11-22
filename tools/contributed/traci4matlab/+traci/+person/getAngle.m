function angle = getAngle(personID)
%getAngle Get the angle of the person.
%   angle = getAngle(PERSONID) Returns the angle in degrees of the named 
%   person within the last step. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getAngle.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
angle = traci.person.getUniversal(constants.VAR_ANGLE, personID);