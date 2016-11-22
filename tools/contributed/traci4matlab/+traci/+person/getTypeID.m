function typeID = getTypeID(personID)
%getTypeID Returns the id of the type of the named person.
%   typeID = getTypeID(PERSONID) Returns the id of the type of the named 
%   person.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getTypeID.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
typeID = traci.person.getUniversal(constants.VAR_TYPE, personID);