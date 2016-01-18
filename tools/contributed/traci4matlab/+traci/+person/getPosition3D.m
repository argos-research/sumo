function position = getPosition3D(personID)
%getPosition3D Returns the position of the named person within the last step.
%   position = getPosition3D(PERSONID) Returns the position of the named 
%   person within the last step [m,m,m].

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPosition3D.m 25 2015-06-25 22:38:10Z afacostag $    

import traci.constants
position = traci.person.getUniversal(constants.VAR_POSITION3D, personID);